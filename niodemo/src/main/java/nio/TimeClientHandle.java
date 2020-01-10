package nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.SQLOutput;
import java.time.Instant;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * <code>TimeClient</code> description
 *
 * @author sunqiyuan
 * @date 2019-12-30
 */
public class TimeClientHandle implements Runnable {

    public static String QUERY = "QUERY TIME ORDER";
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private boolean stop;

    public TimeClientHandle(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;

        try {
            //step1: 打开socketchannel
            socketChannel = SocketChannel.open();
            // step2:设置为非阻塞，并配置客户端参数
            socketChannel.configureBlocking(false);
            socketChannel.socket().setReuseAddress(true);
            socketChannel.socket().setSendBufferSize(2048);
            socketChannel.socket().setReceiveBufferSize(2048);
            // step3: 创建多路复用
            selector = Selector.open();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    @Override
    public void run() {
        try {
            // step7: 轮询就绪的key
            doConnect();
            while (!stop) {
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
                        e.printStackTrace();
                        if (key != null) {
                            // key和channel 是一起注册的，出现异常时，损坏的channel可能依旧会被不断轮询，所以需要cannel进行取消
                            key.cancel();
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private void doConnect() throws IOException {
        //step4: 联接服务端
        boolean isConnect = socketChannel.connect(new InetSocketAddress(host, port));
        // step5: 判断是否联接成功，联接成功就write并注册到选择器为读
        // 联接成功了就输出(write)，然后注册为等待读
        if (isConnect) {
            // 可以加回调函数
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else {
            // step6: 不成功就注册为等待联接，
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel channel) throws IOException {
        byte[] req = QUERY.getBytes("UTF-8");
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        channel.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            System.out.println("send order to server succeed!");
        }

    }

    public void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel sc = (SocketChannel) key.channel();
            //step8: 处理连接并注册为读
            if (key.isConnectable()) {
                if (sc.finishConnect()) {
                    sc.register(selector, SelectionKey.OP_READ);
                    doWrite(sc);
                } else {
                    System.out.println("connect failed");
                    System.exit(1);//联接失败
                    // key.channel();
                }
            }
            //step9: 异步读取消息到缓冲区
            if (key.isReadable()) {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                // step10: 对bytebuffer消息进行编解码（先decode），如果有半包（一次性未读完），继续读取后续的报文，将解码成功的
                // 消息投递到业务线中进行处理
                if (readBytes > 0) {
                    // 读写完成移动指针,即limit移到position
                    readBuffer.flip();
                    // 按照实际读的字符数申请数组
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("Now is :" + body);
                    this.stop();
                } else if (readBytes < 0) {
                    // readBytes =-1 说明链路已经关闭
                    //关闭链路
                    key.cancel();
                    sc.close();
                } else {
                    // 读到0字节忽略，属于正常场景，说明链路没关闭，但是还没有数据写过来
                }
            }
        }
    }
    private void stop() {
        stop = true;
    }
}
