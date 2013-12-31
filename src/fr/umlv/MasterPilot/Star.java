package fr.umlv.MasterPilot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 *  A star and its display
 * 
 * 
 */
public class Star implements Displayable {

    private final int x;
    private final int y;

    /**
    *  Star constructor
    * 
    * @param x x coordinate of the star
    * @param y y coordinate of the star
    */
    public Star(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
    *  Display star on graphics, using yellow color, Ellipse2D with SIZESTAR constant.
    * 
    * @param graphics Graphics2D where the star will be draw
    */
    @Override
    public void display(Graphics2D graphics) {
        int SIZESTAR = 2;
        graphics.setPaint(Color.yellow);
        graphics.fill(new Ellipse2D.Float(x, y, SIZESTAR, SIZESTAR));
    }

}
