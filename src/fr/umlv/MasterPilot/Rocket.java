package fr.umlv.MasterPilot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
*  Rocket and its display
*/
public class Rocket extends FlyingObject {

    /**
    *  Rocket constructor, create a rocket and its specifications
    *  like density, restition, radius and create its body and its shape.
    * 
    * @param world the world where the rocket will be.
    * @param x x coordinate of the planet.
    * @param y y coordinate of the planet.
    * @param direction direction of the rocket.
    * @param col category of the rocket in collision
    */
    public Rocket(World world, float x, float y, Vec2 direction, CollisionCategory col) {
        super(x,y);
        BodyDef bodydefRocket = new BodyDef();
        bodydefRocket.position.set(x - 372 - 55, y - 270 - 30);
        bodydefRocket.type = BodyType.DYNAMIC;
        setBody(world.createBody(bodydefRocket));
        FixtureDef fdRocket = new FixtureDef();
        CircleShape cs = new CircleShape();
        cs.m_radius = 0.65f;
        fdRocket.filter.categoryBits = col.getBits();
//        if (col == CollisionCategory.PLAYER) {
//            fdRocket.filter.maskBits = CollisionCategory.WORLD.getBits()|CollisionCategory.ENNEMY.getBits();
//        } else if(col==CollisionCategory.ENNEMY){
//            fdRocket.filter.maskBits = CollisionCategory.WORLD.getBits()|CollisionCategory.PLAYER.getBits();
//        }
        fdRocket.shape = cs;
        fdRocket.density = 0.0001f;
        fdRocket.restitution = 1f;
        fdRocket.friction = .0f;
        getBody().setBullet(true);
        getBody().createFixture(fdRocket);
        getBody().setUserData("Rocket");
         getBody().setBullet(true);
        getBody().setLinearVelocity(direction);
    }

    /**
    *  Display the rocket to the Graphics2D
    * 
    * @param graphics Graphics2D where the rocket will be draw.
    */   
    @Override
    public void display(Graphics2D graphics) {
        if (getBody().getContactList() != null) {
            die();
        }
        if (isDead() == false) {
            Shape shape = new Ellipse2D.Float(MasterPilot.toXCoordinates(getBody().getPosition().x) + 55, MasterPilot.toYCoordinates(getBody().getPosition().y) + 30, 5, 2);
            graphics.setPaint(Color.lightGray);
            graphics.fill(shape);
        }
    }

}
