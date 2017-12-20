package client;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;
import shared.Snake;

import java.util.Locale;
import java.util.Scanner;

import static java.awt.event.KeyEvent.VK_SPACE;

enum Rotation {
    NONE, LEFT, RIGHT
}

public class GamePlay extends PApplet implements Runnable {


    public static final int SCREEN_X = 1024;
    public static final int SCREEN_Y = 768;


    private PVector direction;
    private Rotation rotation;
    private Snake snake;
    private Thread gameThread;
    private float foodX;
    private float foodY;
    private NetworkClient socketClient;

    public GamePlay() {
        direction = new PVector(1, 0);
        rotation = Rotation.NONE;
        snake = new Snake(100, 100);
        socketClient = new NetworkClient();
        //socketClient.start();
        this.foodX=300;
        this.foodY=300;
        gameThread = new Thread(this);
        //gameThread.start();

    }

    @Override
    public void settings() {
        size(SCREEN_X, SCREEN_Y, "processing.opengl.PGraphics2D");
    }


    public synchronized void drawSnake(Snake snake) {
        /*for (int i = snake.getParts().size()-1; i>=0; i--) {
            PVector v = snake.getParts().get(i);
            ellipse(v.x, v.y, 20,20);
        }*/

        String newData = socketClient.receiveData();
        String[] data = newData.split("/");

        if (data[0].startsWith("food")) {
            Scanner sc = new Scanner(data[0].substring(4)).useLocale(Locale.US);

            fill(153);
            ellipse(sc.nextFloat(), sc.nextFloat(), 30, 30);
            sc.close();
        }

        textSize((float) 24.0);
        text("Spacebar beendet das Spiel!", 10, 50);
        textSize((float) 18.0);


        for (int i = 1; i < data.length; i++) {
            String[] workData = data[i].split("#");
            if(workData[0].startsWith("ter")){
                socketClient.sendData("ter"+"#"+workData[1]);
                this.stop();
            }else {
                fill(i * 55 + 30);

                Scanner sc = new Scanner(workData[1]).useLocale(Locale.US);
                text(workData[0], sc.nextFloat() + 10, sc.nextFloat() + 10);
                //for (int z = 0; z < 18; z++) {
                int x = 0;
                while (sc.hasNext()) {
                    ellipse(sc.nextFloat(), sc.nextFloat(), 30, 30);
                    System.out.println("length-----" + x);
                    x++;
                }

                sc.close();
            }
        }


    }

    @Override
    public void draw() {
        background(0, 255, 0);

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
            case VK_SPACE:
                socketClient.sendData("ter");
                this.stop();
        }


    }

    @Override
    public void stop() {
        super.stop();
        this.exit();
    }


    @Override
    public void keyReleased() {
        socketClient.sendData("dir" + direction.x + " " + direction.y);
        rotation = Rotation.NONE;
    }

    @Override
    public void run() {

    }

    private void drawFood() {

    }

    public static void main(String[] args) {
        //SnakeTest snakeGame = new SnakeTest();
        //snakeGame.socketClient.start();
        PApplet.main("client.GamePlay");

    }


}