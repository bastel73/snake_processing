package server;

import java.util.Observable;
import java.util.Observer;

/**
 * Reports changes in the life status of the player's character.
 */
public class AliveObserver implements Observer{
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Game && !(boolean)arg){

        }
    }
}
