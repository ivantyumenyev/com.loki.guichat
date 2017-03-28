package server;

/**
 * Created by Svirinstel on 21.03.17.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Svirinstel on 18.03.17.
 */
public class ClientHandler implements Runnable {

    private String clientName;

    private Socket socket;
    private MessageServer messageServer;

    private DataInputStream inStream;
    private DataOutputStream outStream;

    public ClientHandler(Socket socket, MessageServer messageServer) {
        this.socket = socket;
        this.messageServer = messageServer;
        try {
            inStream = new DataInputStream(socket.getInputStream());
            outStream = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        try {
            while (true) {

                String inputMessage = inStream.readUTF();

                if (inputMessage != null) {

                    if (clientName == null) {
                        authClient(inputMessage);
//                    } else if (inputMessage.equalsIgnoreCase("END_SESSION")) {
//                        break;
                    } else {

                        String currentTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                        messageServer.sendMessageToAllClients(currentTime + " " + clientName + ": " + inputMessage);
                    }
                }

                Thread.sleep(100);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMessage(String outMessage) {
        try {
            outStream.writeUTF(outMessage);
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void authClient(String message) {
        String[] auth = message.split("|");
        if (auth[0] != "/auth") return;

        clientName = auth[1];

        System.err.println("Client " + clientName + " was connected.");
    }
}