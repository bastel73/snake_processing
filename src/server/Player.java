package server;

import processing.core.PVector;
import shared.Snake;

public class Player {
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

        returnString.append(playerConnection.getPlayerName()+"#");

        for (PVector vector:snake.getParts()){

            returnString.append(vector.x);
            returnString.append(" "+vector.y+" ");

        }
        returnString.append("/");
        return returnString.toString();
    }

    public Snake getSnake(){
        return snake;
    }

    public PlayerConnection getPlayerConnection() {
        return this.playerConnection;
    }
}
