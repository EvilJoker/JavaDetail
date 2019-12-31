package bioPool;

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
        try (
                Socket socket = new Socket("127.0.0.1", port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {

            out.println(QUERY);
            String body = in.readLine();
            System.out.println("now is " + body.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
