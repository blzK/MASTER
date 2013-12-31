package fr.umlv.MasterPilot;


import java.awt.Graphics2D;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * FlyingObject is an abstract class which represent all object in interaction
 * with the player shuttle like planet, bonus, shuttles, rockets Implements
 * Sprite because a FlyingObject must be draw
 */
public abstract class FlyingObject implements Displayable {

    private Body body;
    private boolean isDead = false;
    private final float x;
    private final float y;

    public FlyingObject(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    /**
     * Apply a linear impulse to the FlyingObject
     *
     * @param impulse the world force vector, usually in Newtons (N).
     * @param position the world position of the point of application.
     * @param graphics Graphics2D of the FlyingObject
     */
    public void applyLinearImpulse(Vec2 impulse, Vec2 position, Graphics2D graphics) {
        body.applyLinearImpulse(impulse, position);
    }

    /**
     * isDead a method in order to know if the flying object is dead or not.
     *
     * @return boolean.
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Die a method to kill the flying object.
     *
     * @return boolean true in success.
     */
    public boolean die() {
        if (isDead == true) {
            isDead = true;
            return false;
        }
        if (body != null) {
            body.getWorld().destroyBody(body);
        }
        this.isDead = true;
        return true;
    }

    /**
     * setBody a method to modify the body of the flying object.
     *
     * @param body is the body to be assigned to the flyingObject.
     */
    protected void setBody(Body body) {
        if (body == null) {
            throw new NullPointerException();
        }
        this.body = body;
    }

    /**
     * setBody a method to get the body of the flying object.
     *
     * @return body the body of the flying object.
     */
    protected Body getBody() {
        return this.body;
    }

}
