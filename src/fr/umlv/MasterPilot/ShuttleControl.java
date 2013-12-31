package fr.umlv.MasterPilot;


import fr.umlv.zen3.KeyboardEvent;
import java.awt.Graphics2D;
import org.jbox2d.common.Vec2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author azathoth
 */
public class ShuttleControl {

    public static void move(MainShuttle mainShuttle, KeyboardEvent event, Graphics2D graphics) {
        if (event != null) {
            double angle = mainShuttle.getAngle();
            switch (event.getKey()) {
                case UP:
                    mainShuttle.applyLinearImpulse(new Vec2((float) Math.cos(angle) * 500, (float) Math.sin(angle) * 500), mainShuttle.getPosition(), graphics);
                    break;
                case DOWN:
                    mainShuttle.applyForce(new Vec2((float) Math.cos(angle + Math.PI) * 1000, (float) Math.sin(angle + Math.PI) * 1000), mainShuttle.getPosition());
                    break;
                case LEFT:
                    mainShuttle.applyTorque(-1000f);
                    break;
                case RIGHT:
                    mainShuttle.applyTorque(1000f);
                    break;
                case SPACE:
                    mainShuttle.fire(graphics, RocketType.ROCKET, 0, 0, null, CollisionCategory.PLAYER);
                    break;
                case B:
                    mainShuttle.fire(graphics, RocketType.ExpBomb, 0, 0, null, CollisionCategory.PLAYER);
                    break;
                case N:
                    mainShuttle.fire(graphics, RocketType.ImpBomb, 0, 0, null, CollisionCategory.PLAYER);
                    break;
                case P:
                    System.out.println("X = " + mainShuttle.getPosition().x + " Y = " + mainShuttle.getPosition().y);
                    break;
            }
        }
    }

}
