/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcptestserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author lachlan
 */
public class TCPTestServer {

    public static final String USAGE = ""
            + "java -jar TCPTestServer.jar <port> [output-file]";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int port;

        if (args.length < 1) {
            System.out.println("Usage:");
            System.out.println(USAGE);

            System.out.println();
            System.out.println("Defaulting to port 80");
            port = 8080;
        } else {
            port = Integer.parseInt(args[0]);
        }

        byte[] buf = new byte[1024];

        try {
            OutputStream out = (args.length == 2) ? openFile(args[1]) : System.out;

            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Ready on port " + port);

            while (true) {
                Socket client = serverSocket.accept();
                InputStream is = openSocketStream(client);

                System.out.println(
                        client.getInetAddress().getHostAddress() + " connected");

                int len;
                while ((len = is.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }

                System.out.println(
                        client.getInetAddress().getHostAddress() + " finished");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static OutputStream openFile(String filename) throws IOException {
        return new BufferedOutputStream(
                new FileOutputStream(filename));
    }

    private static InputStream openSocketStream(Socket client) throws
            IOException {
        return new BufferedInputStream(
                client.getInputStream());
    }
}
