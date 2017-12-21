package server;

import java.util.Observable;
import java.util.Observer;

public class AliveObserver implements Observer{
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Game && !(boolean)arg){

        }
    }
}
