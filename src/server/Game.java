package server;

import processing.core.PVector;
import shared.Snake;

import java.util.HashMap;
import java.util.Map;

public class Game extends Thread{

    class Player {
        final PlayerConnection playerConnection;
        final Snake snake;
        final PVector direction;

        public Player(PlayerConnection playerConnection, Snake snake, PVector direction) {
            this.playerConnection = playerConnection;
            this.snake = snake;
            this.direction = direction;
        }

        public void move() {
            snake.moveBy(direction);
        }

        public String position() {
            return "pos "+ playerConnection.getPlayerName()+" "+snake.head().x+" "+snake.head().y;
        }

        public String getSnakeData(){
            StringBuilder returnString=new StringBuilder();

            returnString.append(playerConnection.getPlayerName()+" ");

            for (PVector vector:snake.getParts()){

                returnString.append(vector.x);
                returnString.append(" "+vector.y+" ");

            }
            returnString.append("/");
            return returnString.toString();
        }
    }

    private final Map<String, Player> players = new HashMap<>();

    public synchronized void registerClient(PlayerConnection playerConnection) {
        System.out.println("GameClient registered: "+ playerConnection.getPlayerName());
        players.put(
                playerConnection.getPlayerName(),
                new Player(
                        playerConnection, new Snake(100,100), new PVector(1,0)
                )
        );
    }

    public synchronized void unregisterClient(PlayerConnection playerConnection) {
        System.out.println("GameClient unregistered: "+ playerConnection.getPlayerName());
        players.remove(playerConnection.getPlayerName());
    }

    @Override
    public void run() {

        while(true) {
            StringBuilder returnString=new StringBuilder();
            try {
                Thread.sleep(30);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Player player : players.values()) {
                player.move();
            }

            for (Player player : players.values()) {
                System.out.println("Player : " + player.position());
                returnString.append(player.getSnakeData());
            }
            broadcast(returnString.toString());
        }
    }

    private void broadcast(String position) {
        for (Player p : players.values()) {
            p.playerConnection.send(position);
        }
    }

    public synchronized void setDirection(String playerName, float x, float y) {
        players.get(playerName).direction.set(x,y).normalize();
    }
}
