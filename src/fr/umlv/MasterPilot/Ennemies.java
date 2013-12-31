/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.umlv.MasterPilot;

import java.awt.Graphics2D;
import java.util.ArrayList;
import org.jbox2d.common.Vec2;

/**
 *
 * @author azathoth
 */
public class Ennemies extends SpaceShuttle {

    ArrayList<SpaceShuttle> listEnnemy;

    public Ennemies(float x, float y) {
        super(x, y);
        this.listEnnemy= new ArrayList<>();
    }

    @Override
    public void display(Graphics2D graphics, MainShuttle mainShuttle) {
        for (SpaceShuttle shuttle : listEnnemy) {
            shuttle.display(graphics, mainShuttle);
        }
    }

    @Override
    public void behave(Vec2 mainShuttlePos, Graphics2D graphics) {
        for (SpaceShuttle shuttle : listEnnemy) {
            shuttle.behave(mainShuttlePos, graphics);
        }
    }

    public void setEnnemies(ArrayList<SpaceShuttle> listEnnemy) {
        this.listEnnemy = listEnnemy;
    }
    
    public boolean isEmpty(){
        return listEnnemy.isEmpty();
                
    
}
}
