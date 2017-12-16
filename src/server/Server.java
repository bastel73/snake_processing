package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Game game = new Game();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(3333);
        game.start();

        while (true) {
            Socket client = server.accept();
            System.out.println("GameClient connected"+" from: "+client.getPort());
            PlayerConnection playerConnection = new PlayerConnection(client, game);
            System.out.println("Player connection established ");

            game.registerClient(playerConnection);
            System.out.println("Player registered : "+playerConnection.getPlayerName());
            playerConnection.start();
        }
    }
}
