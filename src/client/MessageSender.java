package client;

/**
 * Created by Svirinstel on 28.03.17.
 */
public interface MessageSender {
    public boolean login(String userName, String password);
    public void sendMessage(String message);
}
