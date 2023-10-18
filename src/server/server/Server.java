package server.server;

import server.client.Client;
import server.client.ClientGUI;
import server.client.ClientView;

import java.io.FileReader;
import java.util.List;

public class Server implements Repositoriy {
    boolean work;
    public static final String LOG_PATH = "src/server/log.txt";
    List<Client> clientList;
    public boolean connectUser(Client client){
        if (!work){
            return false;
        }
        clientList.add(client);
        return true;
    }

    public String getHistory() {
        return readLog();
    }

    private String readLog(){
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(LOG_PATH);){
            int c;
            while ((c = reader.read()) != -1){
                stringBuilder.append((char) c);
            }
            stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void disconnectUser(Client client){
        clientList.remove(client);
        if (client != null){
            client.disconnectFromServer();
        }
    }
}
