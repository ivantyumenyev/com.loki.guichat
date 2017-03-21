package server;

/**
 * Created by Svirinstel on 21.03.17.
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        final int SERVER_SOCKET = 8189;

        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(SERVER_SOCKET);

            System.out.println("Server is running. Waiting for clients...");

            while (true) {
                try {
                    socket = server.accept();
                    System.out.println("Server has new connection!");

                    new Thread(new ClientHandler(socket)).start();
                    new Thread(new Sender(socket)).start();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                server.close();
                System.out.println("Server is closed.");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}