package aio.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * <code>AcceptCompletionHandler</code>
 * 为了体现异步，所有链接阶段都会有相应的联接状态。在AIO中可以通过CompletionHandler完成这个回调动作的定义。
 * CompletionHandler<V,A>, 是用来消耗asynchronous I/O operation的处理器，
 * V是 I/O 操作的结果的
 * A是attachment,I/O操作的附件，handler执行时获取传递进来的参数
 * <p>
 * CompletionHandler有两个方法
 * + competed是定义执行逻辑
 * + failed 异常处理 Throwable，异常类型的父类
 *
 * @author sunqiyuan
 * @date 2020-01-09
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
        // 利用传入的ServerHandler句柄，传入attachment和绑定当前handle,当handle执行时会将这里的attachment传入，作为A参数
        attachment.asynchronousServerSocketChannel.accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //参数含义dst,attahment,新的handler-->进行读动作，这个类的构造器将result对象传入
        result.read(buffer, buffer,new ReadCompletionHandler(result));

    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();//让线程继续执行下去
    }
}
