package server.server;

import server.client.Client;
import server.client.ClientGUI;
import server.client.ClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame{
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;

    private Server server;
    JButton btnStart, btnStop;
    JTextArea log;


    public ServerWindow(Server server){
        this.server = server;
        //clientGUIList = new List<Client>();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setLocationRelativeTo(null);

        createPanel();

        setVisible(true);
    }

        public void sendMessage(String text){
        if (!server.getWork()) return;
        appendLog(text);
        answerAll(text);
        saveInLog(text);
    }

    private void answerAll(String text){
        for (Client client: server.getClientList()){
            client.serverAnswer(text);
        }
    }

    private void saveInLog(String text){
        try (FileWriter writer = new FileWriter(server.LOG_PATH, true)){
            writer.write(text);
            writer.write("\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

public boolean cheackConnectServer(Client client){
        return server.connectUser(client);
}

    private void appendLog(String text){
        log.append(text + "\n");
    }

    public String loadChathistory(){
        return server.getHistory();
    }
    private void createPanel() {
        log = new JTextArea();
        add(log);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private Component createButtons() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server.getWork()){
                    appendLog("Сервер уже был запущен");
                } else {
                    server.setWork(true);
                    appendLog("Сервер запущен!");
                }
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!server.getWork()){
                    appendLog("Сервер уже был остановлен");
                } else {
                    server.setWork(false);
                    if (server.getClientList() != null) disconnectAllUser();
                    //TODO поправить удаление
                    appendLog("Сервер остановлен!");
                }
            }
        });

        panel.add(btnStart);
        panel.add(btnStop);
        return panel;
    }

    public String disconnectUser(Client client){
        return server.disconnectUser(client);
    }

    public void disconnectAllUser(){
        server.getClientList().clear();
    }
}


