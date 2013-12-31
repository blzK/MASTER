package fr.umlv.MasterPilot;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Planet and its display
 */
public class Planet extends FlyingObject {
    
    static int WIDTH = 800;
    static int HEIGHT = 600;

    /**
     * Planet constructor, create a planet and its specifications like density,
     * restitution, radius and create its body and its shape
     *
     * @param world the world where the planet will be.
     * @param x x coordinate of the planet.
     * @param y y coordinate of the planet.
     */
    public Planet(World world, int x, int y) {
        super(x, y);
        BodyDef bodydefPlanet = new BodyDef();
        bodydefPlanet.position.set(x, y);
        bodydefPlanet.type = BodyType.STATIC;
        setBody(world.createBody(bodydefPlanet));
        FixtureDef fdPlanet = new FixtureDef();
        CircleShape cs = new CircleShape();
        fdPlanet.filter.categoryBits = CollisionCategory.WORLD.getBits();
        fdPlanet.filter.maskBits = CollisionCategory.PLAYER.getBits() | CollisionCategory.WORLD.getBits() | CollisionCategory.ENNEMY.getBits();
        cs.m_radius = 65f;
        fdPlanet.shape = cs;
        fdPlanet.density = 1f;
        fdPlanet.restitution = 1f;
        fdPlanet.friction = 1.0f;
        getBody().createFixture(fdPlanet);
        getBody().setUserData("Planet");
    }

    /**
     * Display the planet to the Graphics2D
     *
     * @param graphics Graphics2D where the rocket will be draw.
     */    
    @Override
    public void display(Graphics2D graphics) {
        Point2D center = new Point2D.Float(getX(),getY());
        RadialGradientPaint paint = new RadialGradientPaint(center, 1000,
                new float[]{
                    0.4f,
                    1f},
                new Color[]{
                    Color.black,
                    new Color(37, 197, 246)
                });
        graphics.setPaint(paint);
        
        Shape shape = new Ellipse2D.Float(MasterPilot.toXCoordinates(getBody().getPosition().x), MasterPilot.toYCoordinates(getBody().getPosition().y), 70f, 70f);
        graphics.fill(shape);
        
    }
    
}
