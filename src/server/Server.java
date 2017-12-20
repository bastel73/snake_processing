package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Game game = new Game();
    private static Thread gameThread=new Thread(game);


    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(3333);
        gameThread.start();

        while (true) {
            Socket client = server.accept();
            System.out.println("NetworkClient connected"+" from: "+client.getPort());
            PlayerConnection playerConnection = new PlayerConnection(client, game);
            System.out.println("Player connection established ");

            game.registerClient(playerConnection);
            System.out.println("Player registered : "+playerConnection.getPlayerName());
            playerConnection.start();

        }
    }
}
