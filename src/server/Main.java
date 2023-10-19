package server;

import server.client.ClientGUI;
import server.server.Server;
import server.server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        ServerWindow serverWindow = new ServerWindow(server);
        new ClientGUI(serverWindow,500);
        new ClientGUI(serverWindow,-500);
    }
}
