package fr.umlv.MasterPilot;


import java.awt.Graphics2D;
import org.jbox2d.common.Vec2;

/**
 * Sprite is a interface of all the objects wich can be drawn
 * 
*/
public interface Displayable {

    /**
     * Display the Sprite on Graphics2D
     *
     * @param graphics Graphics2D object where the sprite will be drawn.
     */
    public void display(Graphics2D graphics);

    default public void display(Graphics2D graphics, MainShuttle mainShuttle) {
        display(graphics);
    }
;
}
