package client;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;
import shared.Snake;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_SPACE;

enum Rotation {
    NONE, LEFT, RIGHT
}

/**
 * Represents a game session and provides the logic for it.
 */
public class GamePlay extends PApplet {

    /**
     * The width of the player window
     */
    public static final int SCREEN_X = 1024;

    /**
     * The height of the player window
     */
    public static final int SCREEN_Y = 768;


    private PVector direction;
    private Rotation rotation;

    private NetworkClient socketClient;

    public GamePlay() {
        direction = new PVector(1, 0);
        rotation = Rotation.NONE;

        this.socketClient = new NetworkClient();

    }

    @Override
    public void settings() {
        size(SCREEN_X, SCREEN_Y, "processing.opengl.PGraphics2D");
    }

    /**
     * Draws the screen elements such as text fields, the foot and snakes
     */
    public void drawSnake() {

        String newData = socketClient.receiveData();
        String[] data = newData.split("/");

        if (data[0].startsWith("food")) {
            Scanner sc = new Scanner(data[0].substring(4)).useLocale(Locale.US);

            fill(128,21,21);
            ellipse(sc.nextFloat(), sc.nextFloat(), 30, 30);
            sc.close();
        }

        textSize((float) 24.0);
        text("Spacebar beendet das Spiel!", 10, 50);
        textSize((float) 18.0);


        for (int i = 1; i < data.length; i++) {
            String[] workData = data[i].split("#");
            if (workData[1].startsWith("ter")) {
                text("Schlange " + workData[0] + " hat die Bande gerammt", 110, 110);
                text("Respawn mit Enter-Taste", 130,130);

                socketClient.sendData("stop");
            } else {
                fill(i * 55 + 30);

                Scanner sc = new Scanner(workData[1]).useLocale(Locale.US);
                text(workData[0], sc.nextFloat() + 10, sc.nextFloat() + 10);


                while (sc.hasNext()) {
                    ellipse(sc.nextFloat(), sc.nextFloat(), 30, 30);
                }

                sc.close();
            }
        }

    }

    @Override
    public void draw() {
        if(this.socketClient.isLoggedIn()) {
            background(0, 255, 0);
            if (rotation == Rotation.LEFT) {
                direction.rotate(-0.1f);
            } else if (rotation == Rotation.RIGHT) {
                direction.rotate(0.1f);
            }
            socketClient.sendData("dir" + direction.x + " " + direction.y);
            drawSnake();
        }
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
                break;
            case VK_ENTER:
                socketClient.sendData("start");
                break;
        }


    }

    @Override
    public void stop() {
        super.stop();
        this.exit();
    }

    @Override
    public void keyReleased() {

        rotation = Rotation.NONE;
    }

    public static void main(String[] args){
        PApplet.main("client.GamePlay");
    }


}