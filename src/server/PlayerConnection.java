package server;


import java.io.*;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class PlayerConnection extends Thread {
    private final Game game;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final String playerName;
    private String line;

    public PlayerConnection(Socket socket, Game game) throws IOException {

        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.game = game;
        System.out.println("Konstruktor");
        // Expect player name as first input
        this.playerName = input.readUTF();


    }

    @Override
    public void run() {

        try {
            while ( (line = input.readUTF()) != null) {
                if (line.startsWith("dir")) {
                    System.out.println(line);
                    Scanner s = new Scanner(line.substring(3)).useLocale(Locale.US);
                    game.setDirection(playerName, s.nextFloat(), s.nextFloat());

                }
            }
            game.unregisterClient(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void send(String msg) {
        try {
            output.writeUTF(msg);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getPlayerName() {
        return playerName;
    }
}
