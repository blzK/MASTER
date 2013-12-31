package fr.umlv.MasterPilot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Timer;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

 /**
 *  ImpBomb, is a class who extends Rocket because it's a special rocket.
 *  A implosion bombe
 * 
 */
public class ImpBomb extends Rocket {

    private final Timer timer;
    private final float trigger = 750;
private final int range=500;
    /**
    *  ImpBomb constructor, create a ImpBomb and its timer to implose.
    * 
    * @param world the world where the ImpBomb will be.
    * @param x x coordinate of the ImpBomb.
    * @param y y coordinate of the ImpBomb.
    * @param vec direction.
    * @param col Collision category of the ImpBomb
    */
    public ImpBomb(World world, float x, float y, Vec2 vec, CollisionCategory col) {
        super(world, x, y, vec,col);
        timer = new Timer();
        timer.reset();
    }

    /**
    *  Display the ImpBomb to the Graphics2D
    * 
    * @param graphics Graphics2D where the ImpBomb will be draw.
    */  
    @Override
    public void display(Graphics2D graphics) {

        if (isDead() == false) {
            super.display(graphics); 
            if (timer.getMilliseconds() > trigger||getBody().getContactList()!=null) {
                die(graphics);
            }
        }
    }

    /**
    *  Die method to know if the ImpBomb has implose.
    * 
    * @param graphics Graphics2D where the ImpBomb will draw.
    * @return boolean 
    */  
    public boolean die(Graphics2D graphics) {
        implode(getBody().getWorld());
        Shape shape = new Ellipse2D.Float(MasterPilot.toXCoordinates(getBody().getPosition().x), MasterPilot.toYCoordinates(getBody().getPosition().y), 70f, 70f);
        graphics.setColor(Color.ORANGE);
        graphics.fill(shape);
        return super.die();
    }

    /**
    *  Implode method implose the bomb and draw its explosion.
    * 
    * @param world The world where the ImpBomb will implose
    */
    private void implode(World world) {
        System.out.println(getBody().getWorld().getBodyCount());
        Body bodyTemp2 = getBody().getWorld().getBodyList().getNext();

        for (int i = 0; i < getBody().getWorld().getBodyCount() - 1; i++) {
            final Body bodyTemp = bodyTemp2;
            RayCastCallback rc = new RayCastCallback() {
                Fixture fixture;
                Vec2 point;
                Vec2 normal;
                float fraction;
                @Override
                public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {
                    this.fixture = fixture;
                    this.point = point;
                    this.normal = normal;
                    this.fraction = fraction;
                    if (new Vec2(getBody().getPosition()).add(bodyTemp.getPosition().negate()).length() < range) {
                        bodyTemp.applyLinearImpulse(new Vec2(bodyTemp.getPosition().add(getBody().getPosition().negate())).negate().mul(10), bodyTemp.getPosition());
                        bodyTemp.applyForce(new Vec2(bodyTemp.getPosition().add(getBody().getPosition().negate())).negate().mul(10), bodyTemp.getPosition());
                    }
                    return fraction;
                }
            };
            world.raycast(rc, getBody().getPosition(), bodyTemp.getPosition());
            bodyTemp2 = bodyTemp2.getNext();
        }
    }
}
