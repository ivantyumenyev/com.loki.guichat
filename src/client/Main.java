package client;

public class Main {

    public static void main(String[] args) {

        MainModel mainModel = new MainModel();
        MainView mainView = new MainView();

        MainController controller = new MainController(mainModel,mainView);
        controller.run();

    }
}
