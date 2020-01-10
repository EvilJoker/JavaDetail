package aio.server;

/**
 * <code>TimeServer</code> description
 *
 * @author sunqiyuan
 * @date 2020-01-08
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        // 单纯的启动
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer,"AIO-AsyncTimeServerHandler-001").start();
    }
}
