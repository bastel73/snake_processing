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
    private Socket socket;
    private boolean running;

    public PlayerConnection(Socket socket, Game game) throws IOException {

        this.socket=socket;
        this.input = new DataInputStream(this.socket.getInputStream());
        this.output = new DataOutputStream(this.socket.getOutputStream());
        this.game = game;

        // Expect player name as first input
        this.playerName = input.readUTF();
        this.running=true;

    }

    @Override
    public void run() {

        try {
                while (running){
                line = input.readUTF();
                if (line.startsWith("dir")) {
                    System.out.println(line);
                    Scanner s = new Scanner(line.substring(3)).useLocale(Locale.US);
                    game.setDirection(playerName, s.nextFloat(), s.nextFloat());

                }
                if (line.startsWith("ter")) {
                    this.input.close();
                    this.output.close();
                    this.socket.close();
                    this.stopThread();
                }
                if (line.startsWith("stop")){
                    //this.running=false;
                    //currentThread().interrupt();

                }
                if(line.startsWith("start")){
                    this.game.reset(playerName);
                    this.game.setDirection(playerName, 1,0);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopThread(){
        game.unregisterClient(this);
        this.running=false;
        currentThread().interrupt();
    }

    /**
     * Sends data to the client via socket connection.
     *
     * @param msg formatted data to be sent
     */
    public void send(String msg) {
        try {
            output.writeUTF(msg);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @return the name chosen by the player.
     */
    public String getPlayerName() {
        return playerName;
    }
}
