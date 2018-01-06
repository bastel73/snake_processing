package server;

import processing.core.PVector;
import shared.Snake;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Represents a game in which several players can participate.
 */
public class Game extends Observable implements Runnable{

    private final Map<String, Player> players = new HashMap<>();

    private PVector food;

    public Game() {
        this.food=new PVector(300,300);
    }

    /**
     * Registers a new user who wants to participate in the current game.
     *
     * @param playerConnection client server connection of the user
     */
    public synchronized void registerClient(PlayerConnection playerConnection) {
        System.out.println("NetworkClient registered: "+ playerConnection.getPlayerName());
        players.put(
                playerConnection.getPlayerName(),
                new Player(
                        playerConnection, new Snake(100,100), new PVector(1,0)
                )
        );

    }

    /**
     * Removes a registered user from the running game.
     *
     * @param playerConnection client server connection of the user
     */
    public synchronized void unregisterClient(PlayerConnection playerConnection) {
        System.out.println("NetworkClient unregistered: "+ playerConnection.getPlayerName());
        players.remove(playerConnection.getPlayerName());
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {

        while(true) {
            StringBuilder returnString=new StringBuilder();
            returnString.append(insertFoodData());
            try {
                Thread.sleep(30);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Player player : players.values()) {
                player.move();
            }

            for (Player player : players.values()) {
                if(player.getSnake().head().dist(food)<5){
                    resetFood();
                    player.getSnake().grow();
                }
                returnString.append(player.getSnakeData());
            }

            //check player player collision
            //doesn't work fully
            for(Player player : players.values()){
                PVector head = player.getSnake().head();
                for(Player enemie : players.values()){
                    for (PVector vector : enemie.getSnake().getParts()) {
                        if(player != enemie) {
                            if(head.dist(vector)<7){
                                player.setPlayerDead();
                            }
                        }
                    }
                }
            }

            broadcast(returnString.toString());

        }
    }

    /**
     * Adds the current x and y position of the food in a string.
     *
     * @return a string with the current x and y position of the food.
     */
    private String insertFoodData(){
        return "food "+food.x+" "+food.y+"/";
    }

    /**
     * Sends position data about players and food to the players.
     *
     * @param broadcastMsg combined position data
     */
    private void broadcast(String broadcastMsg) {
        for (Player p : players.values()) {
            p.playerConnection.send(broadcastMsg);
        }
    }

    /**
     * Defines a new position of the food.
     */
    public void resetFood(){
        food = new PVector((int)((Math.random()*1000)+1), (int)(Math.random()*745)+20);
    }

    /**
     * Sets the direction of movement of a player.
     *
     * @param playerName players name
     * @param x value of x-axis
     * @param y value of y-axis
     */
    public synchronized void setDirection(String playerName, float x, float y) {
        players.get(playerName).direction.set(x,y).normalize();
    }

    /**
     * Reset a single player.
     *
     * @param playerName unique name of the player
     */
    public void reset(String playerName){
        players.get(playerName).reSpawnPlayer();
    }
}
