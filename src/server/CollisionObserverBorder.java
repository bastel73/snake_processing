package server;

import processing.core.PVector;

import java.util.Observable;
import java.util.Observer;

public class CollisionObserverBorder implements Observer {


    @Override
    public void update(Observable o, Object arg) {
            PVector checkVector=(PVector)arg;
            if(checkVector.x<10 || checkVector.x>1014 || checkVector.y<10 || checkVector.y>758){
                if(o instanceof Player){
                    ((Player) o).setPlayerDead();
                }
            }
    }
}
