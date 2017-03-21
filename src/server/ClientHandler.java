package server;

/**
 * Created by Svirinstel on 21.03.17.
 */
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Svirinstel on 18.03.17.
 */
public class ClientHandler implements Runnable {

    private Socket socket;

    private Scanner inStream;
    static int clientsCount;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            inStream = new Scanner(socket.getInputStream());

            clientsCount++;

            System.out.println("Client " + clientsCount + " is connected.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        while (true) {
            if (inStream.hasNext()) {
                String inputMessage = inStream.nextLine();
                if (inputMessage.equalsIgnoreCase("END_SESSION")) break;
                System.out.println("Client " + clientsCount + ": " + inputMessage);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        System.out.println("Client " + clientsCount + " is disconnect");

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}