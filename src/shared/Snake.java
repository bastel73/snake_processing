package shared;

import processing.core.PVector;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 * Represents the player's character.
 */
public class Snake extends Observable {
    private int size = 20;
    private List<PVector> parts = new LinkedList<>();


    public Snake(float x, float y) {
        for (int i=0; i< size; i++) {
            parts.add(new PVector(x,y));
        }
    }

    public void moveTo(PVector newHead) {
        List<PVector> newparts = new LinkedList<>();
        newparts.add(newHead.copy());

        for (int i = 1; i< parts.size(); i++) {
            PVector head = parts.get(i-1);
            PVector tail = parts.get(i);

            PVector diff = PVector.sub(head, tail);
            diff.mult(0.1f);
            newparts.add(PVector.add(tail, diff));
        }

        parts = newparts;
    }

    /**
     * Determines the direction of movement of the snake.
     * @param direction vector of the direction of movement
     */
    public void moveBy(PVector direction) {
        moveTo(PVector.add(head(), direction));
    }

    /**
     * @return the first element of the snake
     */
    public PVector head() {
        return parts.get(0);
    }


    public List<PVector> getParts() {
        return parts;
    }

    /**
     * Makes the snake grow by one element and is typically called when the player has collected food.
     */
    public void grow(){
        this.size++;
        System.out.println("Schlangengröße --->>>>>>"+this.size);
        PVector tail = parts.get(parts.size()-1);
        parts.add(tail);
    }
}
