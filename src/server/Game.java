package server;

import processing.core.PVector;
import shared.Snake;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class Game extends Observable implements Runnable{

    private final Map<String, Player> players = new HashMap<>();

    private PVector food;


    public Game() {
        this.food=new PVector(300,300);


    }

    public synchronized void registerClient(PlayerConnection playerConnection) {
        System.out.println("NetworkClient registered: "+ playerConnection.getPlayerName());
        players.put(
                playerConnection.getPlayerName(),
                new Player(
                        playerConnection, new Snake(100,100), new PVector(1,0)
                )
        );

    }

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
                if(player.getSnake().head().x<20 || player.getSnake().head().y<20 ||
                        player.getSnake().head().x>1000 || player.getSnake().head().y>740){
                    returnString.append("ter"+"#");
                }

                returnString.append(player.getSnakeData());
            }

            broadcast(returnString.toString());

        }
    }

    private String insertFoodData(){
        return "food "+food.x+" "+food.y+"/";
    }

    private void broadcast(String position) {
        for (Player p : players.values()) {
            p.playerConnection.send(position);
        }
    }

    public void resetFood(){
        food=new PVector((int)((Math.random()*1000)+1), (int)(Math.random()*745)+20);
    }

    public synchronized void setDirection(String playerName, float x, float y) {
        players.get(playerName).direction.set(x,y).normalize();
    }

    public void terminatePlayer(String name){
        players.remove(name);
    }

}
