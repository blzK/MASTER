package fr.umlv.MasterPilot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import org.jbox2d.common.Timer;
import org.jbox2d.common.Vec2;

/**
 * Space shuttle is an abstract class  wich extends FlyingObject
 * All shuttle, player and ennemys extends this. It contains method for all shuttle like position
 * and movement.
 */
 
public abstract class SpaceShuttle extends FlyingObject {

    private final ArrayList<Rocket> rockets = new ArrayList<>();
    private final Timer timer= new Timer();
    
    /**
    *  Space shuttle constructor.
    * 
    * @param x x coordinate of the SpaceShuttle.
    * @param y y coordinate of the SpaceShuttle.
    */
    public SpaceShuttle(float x, float y) {
       super(x, y);
    }
    
    /**
    *  Get the timer of space Shuttle
    * 
    * @return timer
    */
    public Timer getTimer(){
        return this.timer;
    }
    
    /**
    *  Reset the timer of space Shuttle
    * 
    */
    public void resetTimer(){
        this.timer.reset();
    }
    abstract public void behave(Vec2 mainShuttlePos, Graphics2D graphics);
    
    /**
    *  Get the position of the space shuttle
    * 
    * @return Vec2 who contains x,y coordinate of the spaceShuttle. 
    */
    public Vec2 getPosition() {
        return getBody().getPosition();
    }

    /**
    *  Get the angle of the space shuttle
    * 
    * @return angle value. 
    */
    double getAngle() {
        return getBody().getAngle();
    }

    /**
    *  Apply an angular impulse to the space shuttle
    * 
    * @param f the angular impulse in units of kg*m*m/s.
    */
    void applyAngularImpulse(float f) {
        getBody().applyAngularImpulse(f);
    }

    /**
    *  Apply a force to the space shuttle
    * 
    * @param vec2 the world force vector, usually in Newtons (N).
    * @param position the world position of the point of application.
    */
    void applyForce(Vec2 vec2, Vec2 position) {
        getBody().applyForce(vec2, position);
    }


    /**
    *  Apply a linear impulse to the space shuttle
    * 
    * @param impulse the world impulse vector, usually in N-seconds or kg-m/s.
    * @param point the world position of the point of application.
    * @param graphics the Graphics2D of the space shuttle
    */
    @Override
    public void applyLinearImpulse(Vec2 impulse, Vec2 point, Graphics2D graphics) {
        graphics.setColor(new Color(1f, 0f, 0f, .5f));
        graphics.fill(new Ellipse2D.Float(getBody().getPosition().x, getBody().getPosition().y + 272, 60, 60));
        super.applyLinearImpulse(impulse, point, graphics); //To change body of generated methods, choose Tools | Templates.

    }

    /**
    *  Apply torque to the space shuttle
    * 
    * @param torque about the z-axis (out of the screen), usually in N-m.
    */
    public void applyTorque(float torque) {
        getBody().applyTorque(torque);
    }

    /**
    *  Display the space shuttle to the Graphics2D
    * 
    * @param graphics Graphics2D where the space shuttle will be draw.
    */  
    @Override
    public void display(Graphics2D graphics) {
        if (!rockets.isEmpty()) {
            for (Rocket rocket : rockets) {
                rocket.display(graphics);
            }
        }
    }

    /**
    *  Fire by the space shuttle to a point, with specified rocket type
    * 
    * @param graphics Graphics2D where the rocket of space shuttle will be draw.
    * @param rocketType Graphics2D where the rocket of space shuttle will be draw.
    * @param x X coordinate for direction of fire.
    * @param y Y coordinate for direction of fire.
     * @param direction is the direction in in which the spaceshuttle fires.
     * @param col Collision Category 
    */  
    public void fire(Graphics2D graphics, RocketType rocketType, float x, float y, Vec2 direction,CollisionCategory col) {

        switch (rocketType) {
            case ExpBomb:
                rockets.add(
                        new ExpBomb(getBody().getWorld(), x, y, direction, col));
                break;
            case ImpBomb:
                rockets.add(
                        new ImpBomb(
                                getBody().getWorld(), x, y, direction, col));
                break;
            default:
                rockets.add(
                        new Rocket(
                                getBody().getWorld(), x, y, direction, col));
                break;

        }
    }

    

}
