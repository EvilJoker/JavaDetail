package bioPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

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
            System.out.println("Time Server with pool start in port: " + port);
            TimeServerHandlerExecutePool executePool = new TimeServerHandlerExecutePool(50,1000);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                executePool.execute(new TimeServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
