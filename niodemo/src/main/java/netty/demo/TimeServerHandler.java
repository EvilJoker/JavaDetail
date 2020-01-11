package netty.demo;

import java.time.Instant;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * <code>TimeServerHandler</code> description
 *
 * @author sunqiyuan
 * @date 2020-01-11
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 此处的byteBuf不是byteBuffer
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("The time server receive order : " + body);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? Instant.now().toString() : "BAD ORDER";
        //消息转化，类似于bytebuffer不过功能更强大
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resp);

    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        // 将消息发送，从性能角度考虑为了防止频繁的唤醒Selector进行消息发送，Netty的wirte并不直接将消息
        // 而是调用write只是把代发送的消息发送到缓冲数组中，再通过掉过fluse方法，将发送缓冲区中的消息全部写到SocketChannel中

        ctx.flush();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        // 发生异常时 释放资源
        ctx.close();
    }
}
