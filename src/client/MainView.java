package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Svirinstel on 21.03.17.
 */
public class MainView extends JFrame{

    private MessageSender sender;

//    Authorization
    private JPanel authPanel;
    private JButton loginBtn;
    private JTextField nameFld;
    private JPasswordField passFld;

//    Message
    private JPanel messagePanel;
    private JTextField messageFld;
    private JButton sendMessageBtn;

//    Chat list
    private JTextArea chatArea;

    public MainView() {

        initMainFrame();
        initChatPanel();
        initAuthPanel();

    }

    public void setSender(MessageSender sender) {
        this.sender = sender;
    }

    private void initMainFrame(){
        setTitle("Welcome to loki chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(250,250,600,450);
        setLayout(new BorderLayout());
    }

    private void initChatPanel(){
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        add(listPanel,BorderLayout.CENTER);

        chatArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(chatArea);
        listPanel.add(scrollPane);

    }

    public void initAuthPanel(){
        authPanel = new JPanel();
        add(authPanel,BorderLayout.SOUTH);

        ActionListener loginAct = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        };

//        User name
        JPanel namePanel = new JPanel();
        authPanel.add(namePanel);

        JLabel nameLbl = new JLabel("Name");
        namePanel.add(nameLbl);

        nameFld = new JTextField(10);
        namePanel.add(nameFld);

        nameLbl.setLabelFor(nameFld);

        nameFld.addActionListener(loginAct);

//        User password
        JPanel passPanel = new JPanel();
        authPanel.add(passPanel);

        JLabel passLbl = new JLabel("Password");
        passPanel.add(passLbl);

        passFld = new JPasswordField(10);
        authPanel.add(passFld);

        passLbl.setLabelFor(passFld);

        passFld.addActionListener(loginAct);

//        Login to chat
        loginBtn = new JButton("Login");
        authPanel.add(loginBtn);

        loginBtn.addActionListener(loginAct);

    }

    private void login(){

        String login = nameFld.getText();
        String password = String.valueOf(passFld.getPassword());

        if (sender.login(login,password)){
            authPanel.setVisible(false);
            initMessagePanel();
        }

    }

    public void initMessagePanel(){
        messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        add(messagePanel,BorderLayout.SOUTH);

        ActionListener sendMessageAct = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        };

//        Message
        JLabel messageLbl = new JLabel("Message:");
        messagePanel.add(messageLbl,BorderLayout.WEST);

        messageFld = new JTextField();
        messagePanel.add(messageFld,BorderLayout.CENTER);

        messageFld.grabFocus();

        messageFld.addActionListener(sendMessageAct);

//        Send message
        sendMessageBtn = new JButton("Send");
        messagePanel.add(sendMessageBtn,BorderLayout.EAST);

        sendMessageBtn.addActionListener(sendMessageAct);
    }

    private void sendMessage(){

    }

    public void addChatMessage(String message){
        chatArea.append(message);
    }

}
