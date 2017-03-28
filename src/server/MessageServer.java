package server;

/**
 * Created by Svirinstel on 26.03.17.
 */
public interface MessageServer {

    public void sendMessageToAllClients(String message);
    public void removeFromClientsList(ClientHandler client);
}
