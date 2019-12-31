package bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <code>Bio</code> description
 *
 * @author sunqiyuan
 * @date 2019-12-27
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Time Server start in port: " + port);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
