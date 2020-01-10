package aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * <code>AsyncTimeServerHandler</code> description
 *
 * @author sunqiyuan
 * @date 2020-01-08
 */
public class AsyncTimeServerHandler implements Runnable {
    private int port;
    // 作用是允许当前线程一致阻塞，防止线程执行完就退出。真实的业务中不需要额外的线程处理asynchronousSocketChannel，这里是为了演示
    CountDownLatch latch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try {
            //step1: 启动异步server channel
            asynchronousServerSocketChannel = asynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("the Time Server is start in port" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // 这里面不需要while循环，线程会卡在latch.await();每个新接入的链接会自动执行一系列的handler,来一个完成一个
        latch = new CountDownLatch(1);
        doAccept();
        try{
            System.out.println("12345");
            latch.await();
            System.out.println("12345231");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void doAccept(){
        // 把当前对象传入attachment，并且绑定一个handler 回调函数
        asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
    }
}
