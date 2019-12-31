package bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Instant;

/**
 * <code>TimeServerHandler</code> description
 *
 * @author sunqiyuan
 * @date 2019-12-30
 */
public class TimeServerHandler implements Runnable {
    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * 向sockets输出时间
     */
    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);
            String currentTime = null;
            // 不可以使用 while读 会卡死
            String body = in.readLine();

            System.out.println("The time order receive order: " + body);
            currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body.toString()) ? Instant.now().toString() : "BAD ORDER";
            System.out.println(currentTime);
            // 发送完后就会close
            System.out.println(socket.isInputShutdown());
            out.println(currentTime);

            System.out.println(socket.isInputShutdown());

        } catch (IOException e) {
            e.printStackTrace();
            if (out != null) {
                out.close();
                out = null;
            }
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (this.socket != null) {
                try {
                    socket.close();
                    socket = null;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
}
