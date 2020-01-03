package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.Instant;
import java.util.Iterator;
import java.util.Set;

/**
 * <code>MultiplexerTimeServer</code> description
 *
 * @author sunqiyuan
 * @date 2020-01-02
 */
public class MultiplexerTimeServer implements Runnable {
    // nio选择器，默认使用epoll
    private Selector selector;
    // 相当于Serversocket
    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    public MultiplexerTimeServer(int port) throws IOException {
        try {
            // step1: 打开ServerSocketChannel,它是所有客户端联接的父管道
            serverSocketChannel = ServerSocketChannel.open();
            // step2: 绑定监听端口，设置为非阻塞
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            serverSocketChannel.configureBlocking(false);
            // step3: 创建Rector线程，启动多路复用
            selector = Selector.open();
            // step4: 将serverSocketChannel 注册到Selector上，监听ACCEPT事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("TimeServer start in port :" + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        // step5: 无限轮询就绪的key
        while (!stop) {
            try {
                selector.select(1000); //每秒轮询一次,返回就绪的key数量
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();

                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            // key和channel 是一起注册的，出现异常时，损坏的channel可能依旧会被不断轮询，所以需要cannel进行取消
                            key.cancel();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //step6: 处理接入请求,进行三次握手协议，建立连接
            if (key.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                //step7: 新接入的联接，设置为非阻塞，reuse比较底层，可以自行百度
                socketChannel.configureBlocking(false);
                socketChannel.socket().setReuseAddress(true);
                //step8: 新接入的联接，向selector注册为读操作
                socketChannel.register(selector, SelectionKey.OP_READ);

            }
            //step9: 异步读取消息到缓冲区
            if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(byteBuffer);

                // step10: 对bytebuffer消息进行编解码（先decode），如果有半包（一次性未读完），继续读取后续的报文，将解码成功的
                // 消息投递到业务线中进行处理
                if (readBytes > 0) {
                    // 读写完成移动指针,即limit移到position
                    byteBuffer.flip();
                    // 按照实际读的字符数申请数组
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    while (body.endsWith(System.lineSeparator())) {
                        body = body.substring(0, body.length() - System.lineSeparator().length());
                    }
                    System.out.println("The Time Server reciver order :" + body);
                    String currentTime =
                            "QUERY TIME ORDER".equalsIgnoreCase(body.toString()) ? Instant.now().toString() :
                                    "BAD ORDER";
                    System.out.println(currentTime);
                    // step11: 完成写操作
                    currentTime += System.lineSeparator();
                    doWrite(socketChannel, currentTime);
                } else if (readBytes < 0) {
                    // readBytes =-1 说明链路已经关闭
                    //关闭链路
                    key.cancel();
                    socketChannel.close();
                } else {
                    // 读到0字节忽略，属于正常场景，说明链路没关闭，但是还没有数据写过来
                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }

}

