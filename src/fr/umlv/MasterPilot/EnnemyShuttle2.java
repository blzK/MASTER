package fr.umlv.MasterPilot;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
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
public class EnnemyShuttle2 extends SpaceShuttle {

    private int shieldNumber=3;
    
    public EnnemyShuttle2(float x, float y, World world) {
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

    }

    @Override
    public void display(Graphics2D graphics) {
        super.display(graphics);
        if (getBody().getContactList() != null) {
            if(shieldNumber<=0){
                die();
            }else{
                graphics.setColor(new Color(1f, 0f, 0f, .5f));
            graphics.fill(new Ellipse2D.Float(MasterPilot.toXCoordinates(getBody().getPosition().x)+100, MasterPilot.toYCoordinates(getBody().getPosition().y)-100, 120, 120));
                shieldNumber--;
            }
        }
        if (isDead() == false) {
            graphics.setColor(Color.RED);
            AffineTransform transform = new AffineTransform();
            transform.rotate(getBody().getAngle(),
                    MasterPilot.toXCoordinates(getBody().getPosition().x), MasterPilot.toYCoordinates(getBody().getPosition().y)
            );
            Shape transformed = transform.createTransformedShape(new Rectangle2D.Float(MasterPilot.toXCoordinates(getBody().getPosition().x), MasterPilot.toYCoordinates(getBody().getPosition().y), 100, 20));
            graphics.fill(transformed);
        }
    }

    @Override
    public void behave(Vec2 mainShuttlePos, Graphics2D graphics) {
        Vec2 vecDiff = mainShuttlePos.sub(this.getPosition());

        if (isDead() == false) {
            if (getTimer().getMilliseconds() > 200) {
                float posX = MasterPilot.toYCoordinates(getBody().getPosition().x) + 100;
                float posY = MasterPilot.toYCoordinates(getBody().getPosition().y) - 100;
                float pos1 = (float) (MasterPilot.toXCoordinates(getBody().getPosition().x) +Math.cos(getBody().getAngle())) ;
                float pos2 = (float) (MasterPilot.toYCoordinates(getBody().getPosition().y) +Math.sin(getBody().getAngle()));
                float pos3 = (float) (MasterPilot.toXCoordinates(getBody().getPosition().x) +Math.cos(getBody().getAngle())*30) ;
                float pos4 = (float) (MasterPilot.toYCoordinates(getBody().getPosition().y) +Math.sin(getBody().getAngle())*30);
                float pos5 = (float) (MasterPilot.toXCoordinates(getBody().getPosition().x) +Math.cos(getBody().getAngle())*60) ;
                float pos6 = (float) (MasterPilot.toYCoordinates(getBody().getPosition().y) +Math.sin(getBody().getAngle())*60);
                float pos7 = (float) (MasterPilot.toXCoordinates(getBody().getPosition().x) +Math.cos(getBody().getAngle())*90) ;
                float pos8 = (float) (MasterPilot.toYCoordinates(getBody().getPosition().y) +Math.sin(getBody().getAngle())*90);
                fire(graphics, RocketType.ROCKET, pos1, pos2, vecDiff, CollisionCategory.ENNEMY);
                fire(graphics, RocketType.ROCKET, pos3, pos4, vecDiff, CollisionCategory.ENNEMY);
                fire(graphics, RocketType.ROCKET, pos5, pos6, vecDiff, CollisionCategory.ENNEMY);
                fire(graphics, RocketType.ROCKET, pos7, pos8, vecDiff, CollisionCategory.ENNEMY);
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
                } else if(vecDiff.length() < 300){
                    this.applyForce(vecDiff.negate().mul(0.3f), this.getPosition());
                }
            }
        }

    }
}
