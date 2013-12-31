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
 *  ExpBomb, is a class which extends Rocket because it's a special rocket.
 *  A explosion bombe
 * 
 */
public class ExpBomb extends Rocket {

    private final Timer timer;
    private final float trigger = 750;
    private final int range=500;
    /**
    *  ExpBomb constructor, create a ExpBomb and its timer to explose.
    * 
    * @param world the world where the ImpBomb will be.
    * @param x x coordinate of the ImpBomb.
    * @param y y coordinate of the ImpBomb.
    * @param vec direction.
     * @param col Collision Category
    */
    public ExpBomb(World world, float x, float y, Vec2 vec, CollisionCategory col) {
        super(world, x, y, vec, col);
        timer = new Timer();
    }
    /**
    *  Display the ExpBomb on Graphics2D
    * 
    * @param graphics Graphics2D where the ExpBomb will be draw.
    */
    @Override
    public void display(Graphics2D graphics) {
        if (isDead() == false) {
            super.display(graphics); 
            if (timer.getMilliseconds() > trigger || getBody().getContactList() != null) {
                die(graphics);
            }
        }
    }
    /**
    *  Explode, to order the explosion of the ExpBomb.
    * 
     * @param graphics the Graphics2D object where ExpBomb is to be drawn.
     * @return false if already dead.
    */
    public boolean die(Graphics2D graphics) {
        explode(getBody().getWorld());
        Shape shape = new Ellipse2D.Float(MasterPilot.toXCoordinates(getBody().getPosition().x), MasterPilot.toYCoordinates(getBody().getPosition().y), 70f, 70f);
        graphics.setColor(Color.RED);
        graphics.fill(shape);
        return super.die(); 
    }

    private void explode(World world) {
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
                        System.out.println("I colide with " + fixture.getBody().getUserData());
                        System.out.println("with fraction " + fraction);
                        bodyTemp.applyLinearImpulse(new Vec2(bodyTemp.getPosition().add(getBody().getPosition().negate().mul(10))), bodyTemp.getPosition());
                        bodyTemp.applyForce(new Vec2(bodyTemp.getPosition().add(getBody().getPosition().negate())).mul(10), bodyTemp.getPosition());
                    }
                    return fraction;
                }
            };

            world.raycast(rc, getBody().getPosition(), bodyTemp.getPosition());
            bodyTemp2 = bodyTemp2.getNext();
        }
    }
}
