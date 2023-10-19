package server.client;

import server.server.Server;
import server.server.ServerWindow;

public class Client {
    private String name;
    private ClientView clientView;
    private ServerWindow server;
    private boolean connected;

    public Client(ClientView clientView, ServerWindow serverWindow) {
        this.clientView = clientView;
        this.server = serverWindow;
    }

    public boolean connectToServer(String name){
        this.name = name;
        if (server.cheackConnectServer(this)){
            printText("Вы успешно подключились!\n");
            connected = true;
            String log = server.loadChathistory();
            if (log != null){
                printText(log);
            }
            return true;
        } else {
            printText("Подключение не удалось!!\n");
            return false;
        }
    }

    //мы посылаем
    public void sendMessage(String message){
        if (connected) {
            if (!message.isEmpty()) {
                server.sendMessage(name + ": " + message);
            }
        } else {
            printText("Нет подключения к серверу");
        }
    }
    //нам посылают
    public void serverAnswer(String answer){
        printText(answer);
    }

    public void disconnect(){
        if (connected) {
            connected = false;
           // clientView.disconnectFromServer(); // Непонимаю зачем делать это действие. В интерфейсе нет реализации
            printText(server.disconnectUser(this));
        }
    }

    public String getName() {
        return name;
    }

    private void printText(String text){
        clientView.showMessage(text);
    }
}
