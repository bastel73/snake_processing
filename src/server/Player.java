package server;

import processing.core.PVector;
import shared.Snake;

import java.util.Observable;

public class Player extends Observable{
    final PlayerConnection playerConnection;
    final Snake snake;
    final PVector direction;

    private String returnString;
    private boolean alive;

    public Player(PlayerConnection playerConnection, Snake snake, PVector direction) {
        this.playerConnection = playerConnection;
        this.snake = snake;
        this.direction = direction;
        alive = true;
        addObserver(new CollisionObserverBorder());
        addObserver(new CollisionObserverFood());
    }

    /**
     * Moves the snake one step in a certain direction.
     *
     */
    public void move() {
        snake.moveBy(direction);
        setChanged();
        notifyObservers(snake.head());
    }

    /**
     * Provides basic information about the snake required for processing by the client.
     *
     * @return combined string with the name of the snake and positions of the body parts
     */
    public String getSnakeData(){

        if(this.alive) {
            returnString = playerConnection.getPlayerName() + "#";
            for (PVector vector : snake.getParts()) {
                returnString += vector.x + " " + vector.y + " ";
            }
            returnString += "/";
        }
        return returnString;
    }

    /**
     *
     * @return returns the snake object of the player
     */
    public Snake getSnake(){
        return snake;
    }

    /**
     *
     * @return is true when the player is alive.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Marks the player as dead and informs the client.
     */
    public void setPlayerDead(){
        this.alive = false;
        this.returnString = playerConnection.getPlayerName()+"#"+"ter"+"/";
    }

    /**
     * Resets the player status and sets the snake to a standard position.
     */
    public void reSpawnPlayer(){
        System.out.println("Direction vorher :"+this.direction);

        this.direction.set(1,0).normalize();
        this.snake.moveTo(new PVector(300.0f,300.0f));
        this.move();
        System.out.println("Direction :"+this.direction);
        this.alive = true;
    }


}
