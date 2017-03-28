package client;

/**
 * Created by Svirinstel on 28.03.17.
 */
public class MainController implements MessageReceiver, MessageSender{

    MainModel model;
    MainView view;

    public MainController(MainModel model, MainView view) {
        this.model = model;
        model.setReceiver(this);

        this.view = view;
        view.setSender(this);
    }

    public void run(){
        view.setVisible(true);
    }

    @Override
    public void addNewChatMessage(String message) {
        view.addChatMessage(message);
    }

    @Override
    public void sendMessage(String message) {
        model.sendMessage(message);
    }

    @Override
    public boolean login(String userName, String password) {
        return model.authorize( userName, password );
    }
}
