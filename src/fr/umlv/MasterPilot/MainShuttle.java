package fr.umlv.MasterPilot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author azathoth
 */
public class MainShuttle extends SpaceShuttle {

    private boolean isLoaded = false;
    private RocketType rocketType = RocketType.ROCKET;

    /**
    *  MainShuttle constructor, create the main shuttle and its specifications
    *  like density, restition, radius and create its body and its shape
    * 
    * 
    * @param x x coordinate of the main MainShuttle.
    * @param y y coordinate of the MainShuttle.
    * @param world the world where the MainShuttle will be.
    */
    public MainShuttle(float x, float y, World world) {
        super(x, y);
        BodyDef bodydef = new BodyDef();
        bodydef.angle = 0;
        bodydef.bullet = true;

        FixtureDef fd = new FixtureDef();
        CircleShape cs = new CircleShape();
        cs.m_radius = 10;

        fd.filter.categoryBits = CollisionCategory.PLAYER.getBits();
        fd.filter.maskBits = CollisionCategory.WORLD.getBits()|CollisionCategory.BONUS.getBits()|CollisionCategory.ENNEMY.getBits();
        fd.shape = cs;
        fd.density = 0.001f;
        fd.restitution = 1f;

        fd.friction = 1f;

        bodydef.position.set(x, y);

        bodydef.type = BodyType.DYNAMIC;

        setBody(world.createBody(bodydef));
        getBody().setUserData("spaceShuttle");
        getBody().createFixture(fd);
        getBody().setAngularDamping(1);
        getBody().setLinearDamping((float) 0.1);
    }

    /**
    *  Display the main shuttle to the Graphics2D.
    * 
    * @param graphics Graphics2D where the main shuttle will be draw.
    */
    @Override
    public void display(Graphics2D graphics) {
        super.display(graphics); //To change body of generated methods, choose Tools | Templates.
        //SHIELD - CHECK COLLISIONS
        if (getBody().m_contactList != null) {
            graphics.setColor(new Color(1f, 0f, 0f, .5f));
            graphics.fill(new Ellipse2D.Float(MasterPilot.toXCoordinates(getBody().getPosition().x), MasterPilot.toYCoordinates(getBody().getPosition().y), 60, 60));
//                            graphics.fill(new Ellipse2D.Float(getBody().getPosition().x+400, getBody().getPosition().y+300, 60, 60));
        }
//MAIN SHUTTLE DISPLAY        
        float x1Points[] = {getBody().getPosition().x + 800 / 2 - 15, getBody().getPosition().x + 800 / 2 + 30, getBody().getPosition().x + 800 / 2 - 15};
        float y1Points[] = {getBody().getPosition().y + 600 / 2 + 15, getBody().getPosition().y + 600 / 2, getBody().getPosition().y + 600 / 2 - 15};
//                        float x1Points[] = {getBody().getPosition().x + 800 / 2 - 25, getBody().getPosition().x + 800 / 2 + 25, getBody().getPosition().x + 800 / 2 - 25};
//                        float y1Points[] = {getBody().getPosition().y + 600 / 2 + 10, getBody().getPosition().y + 600 / 2, getBody().getPosition().y + 600 / 2 - 10};
        GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x1Points.length);
        polygon.moveTo(x1Points[0], y1Points[0]);

        for (int index = 1; index < x1Points.length; index++) {
            polygon.lineTo(x1Points[index], y1Points[index]);
        }

        polygon.closePath();
        graphics.setColor(Color.darkGray);
//                        graphics.fill(polygon);
        // ANGLE TRANSFORM
        AffineTransform transform = new AffineTransform();
        transform.rotate(getBody().getAngle(),
                getBody().getPosition().x + MasterPilot.WIDTH / 2, getBody().getPosition().y + MasterPilot.HEIGHT / 2
        );
        Shape transformed = transform.createTransformedShape(polygon);
        graphics.fill(transformed);
    }

    /**
    *  Behave is a method which contains the comportment of the mainShuttle.
    * 
    * @param mainShuttlePos position of the mainShuttle
    * @param graphics Graphics2D where the mainShuttle is.
    */ 
    @Override
    public void behave(Vec2 mainShuttlePos,Graphics2D graphics) {

    }

    /**
    *  Fire is method which manage fire of mainShuttle
    * 
    * @param graphics Graphics2D where the mainShuttle is.
    * @param rocketType Type of rocket.
    * @param x x coordinate of the rocket.
    * @param y y coordinate of the rocket.
    * @param direction Direction of the rocket.
    * @param col Collision category of the rocket.
    */ 
    @Override
    public void fire(Graphics2D graphics, RocketType rocketType, float x, float y,Vec2 direction, CollisionCategory col) {
                double angle = getBody().getAngle();
        float posX = (float) (getBody().getPosition().x + MasterPilot.WIDTH / 2 + Math.cos(getBody().getAngle()) * 30 * 1.f) + x;
//        if (Math.cos(getBody().getAngle()) == 0) {
//            posX = (float) (getBody().getPosition().x + MasterPilot.WIDTH / 2 + Math.cos(getBody().getAngle()) * 30 * 1.2f) + 10 + x;
//        }
        float posY = (float) (getBody().getPosition().y + MasterPilot.HEIGHT / 2 + Math.sin(getBody().getAngle()) * 30 * 1.f) + y;
//        if (Math.sin(getBody().getAngle())==0) {
//           posY = (float) (getBody().getPosition().y + MasterPilot.HEIGHT / 2 + Math.sin(getBody().getAngle()) * 30 * 1.2f+10) + y;
//        }
        Vec2 vector = new Vec2((float) (Math.cos(angle) * 100_000_000_000f), (float) Math.sin(angle) * (100_000_000_000f));

        
        
        if (rocketType == RocketType.ROCKET) {
            super.fire(graphics, rocketType, posX, posY,vector, CollisionCategory.PLAYER);

        } else {
            if (isLoaded == true && this.rocketType == rocketType) {
                super.fire(graphics, rocketType, posX, posY,vector, CollisionCategory.PLAYER);
                isLoaded=false;
            }
        }
    }

    /**
    *  setBonus is method which modify the bonus of mainShuttle like ExpBomb or ImpBomb
    *
    * @param type Category of the rocket.
    */ 
    public void setBonus(RocketType type) {
        isLoaded = true;
        this.rocketType = type;
    }
}
