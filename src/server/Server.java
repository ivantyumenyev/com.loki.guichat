package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Svirinstel on 26.03.17.
 */
public class Server implements MessageServer {
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    public Server() {
        final int SERVER_SOCKET = 8189;

        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(SERVER_SOCKET);

            System.out.println("Server is running. Waiting for clients...");

            while (true) {
                try {
                    socket = server.accept();
                    System.out.println("Server has new client!");

                    ClientHandler newClient = new ClientHandler(socket,this);
                    clients.add(newClient);

                    new Thread(newClient).start();

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

    @Override
    public void sendMessageToAllClients(String message) {
        for (ClientHandler client : clients){
            if (client.isConnected()) {
                client.sendMessage(message);
            } else {
                clients.remove(client);
            }
        }
    }

    @Override
    public void removeFromClientsList(ClientHandler client){
        clients.remove(client);
    }
}
