package aio.client;

/**
 * <code>TimeClient</code> description
 *
 * @author sunqiyuan
 * @date 2020-01-10
 */
public class TimeClient {
    public static void main(String[] args) {
        new Thread(new AsyncTimeClientHandler("127.0.0.1", 8080), "AIO-AsyncTimeClientHandler-001").start();
    }

}
