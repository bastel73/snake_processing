package server;

import processing.core.PVector;

import java.util.Observable;
import java.util.Observer;

/**
 * Reports collision with a wall.
 */
public class CollisionObserverBorder implements Observer {


    @Override
    public void update(Observable o, Object arg) {
            PVector checkVector=(PVector)arg;
            if(checkVector.x<20 || checkVector.x>1004 || checkVector.y<20 || checkVector.y>744){
                if(o instanceof Player){
                    ((Player) o).setPlayerDead();
                }
            }
    }
}
