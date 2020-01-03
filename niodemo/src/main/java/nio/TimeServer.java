package nio;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * <code>TimeServer</code> description
 *
 * @author sunqiyuan
 * @date 2020-01-02
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;
        try {
            MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
            new Thread(timeServer, "NIO-multiplexerTimeServer-001").start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
