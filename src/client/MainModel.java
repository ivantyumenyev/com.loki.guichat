package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Svirinstel on 21.03.17.
 */
public class MainModel {

    private Socket socket;
    private DataInputStream inStream;
    private DataOutputStream outStream;

    private Thread inThread;

    private final String SERVER_NAME = "localhost";
    private final int SERVER_SOCKET = 8189;

    public MainModel() {

        try {
            socket = new Socket(SERVER_NAME,SERVER_SOCKET);

            inStream = new DataInputStream(socket.getInputStream());
            outStream = new DataOutputStream(socket.getOutputStream());

            initInputThread();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initInputThread(){
        Thread inThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String message = inStream.readUTF();

                    if (inStream != null) {
                        
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendMessage(String message){
        try {
            outStream.writeUTF(message);
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
