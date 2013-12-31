package fr.umlv.MasterPilot;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
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
public class EnnemysubShuttle3 extends SpaceShuttle {

    public EnnemysubShuttle3(float x, float y, World world) {
        super(x, y);

        BodyDef bodydef2 = new BodyDef();
        bodydef2.angle = 0;
        bodydef2.bullet = true;
        FixtureDef fd = new FixtureDef();
        CircleShape s = new CircleShape();
        s.m_radius = 5;
        fd.filter.categoryBits = CollisionCategory.ENNEMY.getBits();
        fd.filter.maskBits = CollisionCategory.PLAYER.getBits() | CollisionCategory.WORLD.getBits();
        fd.shape = s;
        fd.density = 0.00001f;
        fd.restitution = 1f;
        fd.friction = 1f;
        bodydef2.position.set(x, y);
        bodydef2.type = BodyType.DYNAMIC;
        setBody(world.createBody(bodydef2));
        getBody().setUserData("ennemySubShuttle1");
        getBody().createFixture(fd);
        getBody().setAngularDamping(3);
    }

    @Override
    public void display(Graphics2D graphics) {
        super.display(graphics);
        if (getBody().getContactList() != null) {
            die();
        }
        if (isDead() == false) {
            float posX = MasterPilot.toXCoordinates(getBody().getPosition().x);
            float posY = MasterPilot.toYCoordinates(getBody().getPosition().y);
            float x1Points[] = {posX , posX + 10, posX +3,posX, posX-3, posX-10};
            float y1Points[] = {posY, posY-5, posY +3,posY+15, posY+3,posY-5};
            GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x1Points.length);
            polygon.moveTo(x1Points[0], y1Points[0]);
            for (int index = 1; index < x1Points.length; index++) {
                polygon.lineTo(x1Points[index], y1Points[index]);
            }
            polygon.closePath();
            graphics.setColor(Color.RED);
            AffineTransform transform = new AffineTransform();
            transform.rotate(getBody().getAngle(),
                    posX, posY
            );
            Shape transformed = transform.createTransformedShape((Shape) polygon);
            graphics.fill(transformed);
        }
    }

    @Override
    public void behave(Vec2 mainShuttlePos, Graphics2D graphics) {
        if (isDead() == false) {
            Vec2 vecDiff = mainShuttlePos.sub(getPosition());
            float posX = MasterPilot.toYCoordinates(getBody().getPosition().x) + 100;
            float posY = MasterPilot.toXCoordinates(getBody().getPosition().y) - 100;

            if (vecDiff.length() > 700) {
                this.applyForce(vecDiff.mul(0.2f), this.getPosition());
            } else if (vecDiff.length() < 100) {
                this.applyForce(vecDiff.negate().mul(0.3f), this.getPosition());
            }
            
              double angle = this.getAngle() % (2 * Math.PI) + Math.PI;
                double angleVec = Math.atan2(vecDiff.x, vecDiff.y);
                if (angle > angleVec) {
                    this.applyTorque(0.002f);
                } else {
                    this.applyTorque(-0.002f);
                }
            if (getTimer().getMilliseconds() > 500) {
                fire(graphics, RocketType.ROCKET, posX, posY, vecDiff, CollisionCategory.ENNEMY);
                getTimer().reset();
            }
        }
    }

}
