package nio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * <code>TimeClient</code> description
 *
 * @author sunqiyuan
 * @date 2019-12-30
 */
public class TimeClient {

    public static String QUERY = "QUERY TIME ORDER";

    public static void main(String[] args) {
        int port = 8080;
        try {
            new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClent1").start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
