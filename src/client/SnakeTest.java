package client;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import processing.event.KeyEvent;
import server.GameServer;
import shared.Snake;

import java.util.Locale;
import java.util.Scanner;

enum Rotation {
    NONE, LEFT, RIGHT
}

public class SnakeTest extends PApplet implements Runnable{


    public static final int SCREEN_X = 1024;
    public static final int SCREEN_Y = 768;


    private PVector direction;
    private Rotation rotation;
    private Snake snake;

    private GameClient socketClient;
    private GameServer gameServer;


    public SnakeTest(){
        direction = new PVector(1,0);
        rotation = Rotation.NONE;
        snake= new Snake(100,100);
        socketClient = new GameClient("localhost");
        //socketClient.start();
        Thread gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void settings() {
        size(SCREEN_X,SCREEN_Y, "processing.opengl.PGraphics2D");
    }


    public void drawSnake(Snake snake) {
        /*for (int i = snake.getParts().size()-1; i>=0; i--) {
            PVector v = snake.getParts().get(i);
            ellipse(v.x, v.y, 20,20);
        }*/

        String data=socketClient.receiveData();
        String[] newData=data.split("/");
        System.out.println("Feld --------->"+newData.length);
        String playerID=newData[0].substring(0,4);
        System.out.println("PlayerID : "+playerID);
        textSize((float) 18.0);


        for (int i=0;i<newData.length; i++) {
            fill(i*55+30);
            playerID=newData[i].substring(0,4);
            Scanner sc = new Scanner(newData[i].substring(4)).useLocale(Locale.US);

            text(playerID, sc.nextFloat()+10, sc.nextFloat()+10);
            for (int z = 0; z < 18; z++) {

                ellipse(sc.nextFloat(), sc.nextFloat(), 30, 30);
            }
        }



    }

    @Override
    public void draw() {
        background(0,255,0);

        if (rotation == Rotation.LEFT) {
            direction.rotate(-0.1f);
        } else if (rotation == Rotation.RIGHT) {
            direction.rotate(0.1f);
        }

        snake.moveBy(direction);
        drawSnake(snake);


    }

    @Override
    public void keyPressed(KeyEvent event) {

        switch (event.getKeyCode()) {
            case 37:
                rotation = Rotation.LEFT;
                break;
            case 39:
                rotation = Rotation.RIGHT;
                break;
        }



    }

    @Override
    public void keyReleased() {
        socketClient.sendData("dir"+direction.x+" "+direction.y);
        rotation = Rotation.NONE;
    }

    @Override
    public synchronized void run(){

    }

    public static void main(String[] args) {
        //SnakeTest snakeGame = new SnakeTest();
        //snakeGame.socketClient.start();
        PApplet.main("client.SnakeTest");
        String[] array = PFont.list();
        for(int i=0;i<array.length;i++){
            System.out.println(array[i]);
        }

    }
    }