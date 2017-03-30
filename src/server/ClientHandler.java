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

    private boolean connected;

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
                        connected = true;
                        sendConnectedStatus();
                    } else {
                        sendMessageToAllClients(inputMessage);
                    }
                }

                Thread.sleep(100);

            }
        } catch (IOException e) {
//            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
            sendConnectedStatus();
            System.err.println("Client " + clientName + " disconnected.");
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

    public synchronized boolean isConnected(){
        return connected;
    }

    private synchronized void authClient(String message) {
        String[] auth = message.split("\\|");

        switch (auth[0]){
            case "/auth" :
                clientName = auth[1];
                System.err.println("The client " + clientName + " is authorized.");
                break;
        }
    }

    private void sendConnectedStatus(){
        String currentTime = getCurrentTime();
        String state = connected ? "connected" : "disconnected";
        messageServer.sendMessageToAllClients(currentTime + " Client " + clientName + " is " + state);
    }

    private synchronized void sendMessageToAllClients(String message){
        String currentTime = getCurrentTime();
        messageServer.sendMessageToAllClients(currentTime + " " + clientName + ": " + message);
    }

    private String getCurrentTime(){
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }
}