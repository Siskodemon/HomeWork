package server.server;

import server.client.Client;
import server.client.ClientGUI;
import server.client.ClientView;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Server {
    boolean work;
    public static final String LOG_PATH = "src/server/log.txt";
    List<Client> clientList;

    public boolean connectUser(Client client) {
        if (work) {
            if (clientList == null){
                clientList = new ArrayList<>();
                clientList.add(client);
                return true;
            } else {
                for (Client user:clientList
                     ) {
                    if (user.getName().equals(client.getName()))
                        return false;
                }
                clientList.add(client);
                return true;
            } }else
                return false;

    }

    public boolean getWork() {
        return work;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setWork(boolean status) {
        this.work = status;
    }

    public String getHistory() {
        return readLog();
    }

    private String readLog() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(LOG_PATH);) {
            int c;
            while ((c = reader.read()) != -1) {
                stringBuilder.append((char) c);
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String disconnectUser(Client client) {
        if (clientList.contains(client)) {
            clientList.remove(client);
            return "Вы были отключены от сервера!\n";
        } else return "Вы не были подключены к серверу!\n";
    }
}