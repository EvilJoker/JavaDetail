package aio.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.time.Instant;

/**
 * <code>ReadCompletionHandler</code> description
 *
 * @author sunqiyuan
 * @date 2020-01-09
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel asynchronousSocketChannel;

    public ReadCompletionHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
        if (this.asynchronousSocketChannel == null) {
            this.asynchronousSocketChannel = asynchronousSocketChannel;
        }

    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        // accepthandler中读过了，内容在attachment,所以直接ge更新新buffer指针
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String req = new String(body, "UTF-8");
            System.out.println("The time server receive order :" + req);
            String current = "QUERY TIME ORDER".equals(req) ? Instant.now().toString() : "BAD ORDER";
            doWrite(current);
        } catch (UnsupportedEncodingException e) {

        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try{
            asynchronousSocketChannel.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void doWrite(String currentTime) {
        if (currentTime != null && currentTime.trim().length() > 0) {
            byte[] bytes = currentTime.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();

            //匿名类写法，也是将write行为写成一个handler
            asynchronousSocketChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    if (buffer.hasRemaining()) {
                        asynchronousSocketChannel.write(buffer, buffer, this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer buffer) {
                    try{
                        asynchronousSocketChannel.close();
                    }catch (IOException e){
                        //ignore
                    }
                }
            });
        }
    }
}
