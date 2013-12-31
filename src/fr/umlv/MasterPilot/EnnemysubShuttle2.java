package fr.umlv.MasterPilot;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import org.jbox2d.collision.shapes.PolygonShape;
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
public class EnnemysubShuttle2 extends SpaceShuttle {

    public Shape shape;

    public EnnemysubShuttle2(float x, float y, World world) {
        super(x, y);

        BodyDef bodydef2 = new BodyDef();
        bodydef2.angle = 0;
        bodydef2.bullet = true;
        FixtureDef fd = new FixtureDef();
        PolygonShape s = new org.jbox2d.collision.shapes.PolygonShape();
        s.setAsBox(10, 10);
        fd.filter.categoryBits = CollisionCategory.ENNEMY.getBits();
        fd.filter.maskBits = CollisionCategory.PLAYER.getBits() | CollisionCategory.WORLD.getBits();
        fd.shape = s;
        fd.density = 0.008f;
        fd.restitution = 1f;
        fd.friction = 1f;
        bodydef2.position.set(x, y);
        bodydef2.type = BodyType.DYNAMIC;
        setBody(world.createBody(bodydef2));
        getBody().setUserData("ennemySubShuttle2");
        getBody().createFixture(fd);
        getBody().setAngularDamping(3000);
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
            Shape s = new Rectangle2D.Float(posX, posY, 20, 20);
            graphics.setColor(Color.RED);
            AffineTransform transform = new AffineTransform();
            transform.rotate(getBody().getAngle(), posX, posY);
            Shape transformed = transform.createTransformedShape(s);
            shape = transformed;
            graphics.fill(transformed);
        }
    }

    @Override
    public boolean die() {
        return super.die(); 
    }

    @Override
    public void fire(Graphics2D graphics, RocketType rocketType, float x, float y, Vec2 direction, CollisionCategory col) {
        // DO NOTHING
    }

    public void behave(Vec2 mainShuttlePos, Graphics2D graphics) {
        if (isDead() == false) {
            Vec2 vecDiff = mainShuttlePos.sub(getPosition());
            float posX = MasterPilot.toYCoordinates(getBody().getPosition().x) + 100;
            float posY = MasterPilot.toXCoordinates(getBody().getPosition().y) - 100;

            if (vecDiff.length() > 350) {
                this.applyForce(vecDiff.mul(0.02f), this.getPosition());
            } else if (vecDiff.length() < 250) {
                this.applyForce(vecDiff.negate().mul(0.03f), this.getPosition());

            }

            double angle = this.getAngle() % (2 * Math.PI) + Math.PI;
            double angleVec = Math.atan2(vecDiff.x, vecDiff.y);//+Math.PI*2;
            if (angle > angleVec) {
                this.applyAngularImpulse(0.000001f);
            } else {
                this.applyAngularImpulse(-0.000001f);
            }
            if (getTimer().getMilliseconds() > 500) {
                fire(graphics, RocketType.ROCKET, posX, posY, vecDiff, CollisionCategory.ENNEMY);
                getTimer().reset();
            }
        }
    }
}
