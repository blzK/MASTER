package fr.umlv.MasterPilot;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Timer;
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
public class EnnemyShuttle4 extends SpaceShuttle {

    private final Timer timerShapeShitfting;

    public EnnemyShuttle4(float x, float y, World world) {
        super(x, y);

        BodyDef bodydef2 = new BodyDef();
        bodydef2.angle = 0;
        bodydef2.bullet = true;
        FixtureDef fd = new FixtureDef();
        PolygonShape s = new org.jbox2d.collision.shapes.PolygonShape();
        s.setAsBox(100, 20);
        fd.filter.categoryBits = CollisionCategory.ENNEMY.getBits();
        fd.filter.maskBits = CollisionCategory.PLAYER.getBits() | CollisionCategory.WORLD.getBits();
        fd.shape = s;
        fd.density = 0.0001f;
        fd.restitution = 1f;
        fd.friction = 1f;

        bodydef2.position.set(x, y);
        bodydef2.type = BodyType.DYNAMIC;
        setBody(world.createBody(bodydef2));
        getBody().setUserData("ennemy2");
        getBody().createFixture(fd);
        getBody().setAngularDamping(3);
        timerShapeShitfting = new Timer();
        timerShapeShitfting.reset();

    }

    @Override
    public boolean die() {
        return super.die(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void display(Graphics2D graphics) {
        super.display(graphics);
        if (getBody().getContactList() != null) {
            if (isActive()) {
                die();
            }
        }
        if (isDead() == false) {
            if (isActive()) {
                graphics.setColor(Color.RED);
                float posX = MasterPilot.toXCoordinates(getBody().getPosition().x);
                float posY = MasterPilot.toYCoordinates(getBody().getPosition().y);
                float x1Points[] = {
                    posX - 10, posX + 10, posX + 30, posX + 30, posX + 10, posX - 10, posX - 30, posX - 30
                };
                float y1Points[] = {
                    posY - 30, posY - 30, posY - 10, posY + 10, posY + 30, posY + 30, posY + 10, posY - 10
                };
                GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x1Points.length);
                polygon.moveTo(x1Points[0], y1Points[0]);

                for (int index = 1; index < x1Points.length; index++) {
                    polygon.lineTo(x1Points[index], y1Points[index]);
                }

                polygon.closePath();
                graphics.setPaint(Color.RED);
                AffineTransform transform = new AffineTransform();
                transform.rotate(getBody().getAngle(),
                        MasterPilot.toXCoordinates(getBody().getPosition().x), MasterPilot.toYCoordinates(getBody().getPosition().y)
                );

                Shape transformed = transform.createTransformedShape((Shape) polygon);
                graphics.fill(transformed);
            } else {
                Point2D center = new Point2D.Float(getX(), getY());
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
    }

    private boolean isActive()

    {
return timerShapeShitfting.getMilliseconds() % 20000 < 10000;
    }

    @Override
    public void behave(Vec2 mainShuttlePos, Graphics2D graphics) {
        Vec2 vecDiff = mainShuttlePos.sub(this.getPosition());

        if (isDead() == false) {
            if (isActive()) {
                if (getTimer().getMilliseconds() > 200) {
                    float posX = MasterPilot.toYCoordinates(getBody().getPosition().x) + 100;
                    float posY = MasterPilot.toYCoordinates(getBody().getPosition().y) ;
                    fire(graphics, RocketType.ROCKET, posX, posY, vecDiff, CollisionCategory.ENNEMY);
                    getTimer().reset();
                } else {
                    double angle = this.getAngle() % (2 * Math.PI) + Math.PI;
                    double angleVec = Math.atan2(vecDiff.x, vecDiff.y);//+Math.PI*2;
                    if (angle > angleVec) {
                        this.applyAngularImpulse(-10f);
                    } else {
                        this.applyAngularImpulse(10f);
                    }
                    if (vecDiff.length() > 400) {
                        this.applyForce(vecDiff.mul(0.2f), this.getPosition());
                    } else if (vecDiff.length() < 300) {
                        this.applyForce(vecDiff.mul(0.2f).negate(), this.getPosition());

                    }
                    
                this.applyForce(vecDiff.skew().mul((float)(Math.random()*2-1)).mul(0.1f), this.getPosition());
                }
            } else {

                this.applyForce(getBody().getLinearVelocity().negate(), this.getPosition());
            }
        }

    }
}
