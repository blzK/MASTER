package fr.umlv.MasterPilot;


import java.awt.Graphics2D;
import org.jbox2d.common.Vec2;
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
public class EnnemyShuttle3 extends SpaceShuttle {

    SpaceShuttle[] ennemyGroup = new SpaceShuttle[8];

    public EnnemyShuttle3(float x, float y, World world) {
        super(x, y);
        float posX = x;
        float posY = y;
        for (int i = 0; i < ennemyGroup.length; i++) {

            ennemyGroup[0] = new EnnemysubShuttle2(posX - 10, posY - 10, world);
            ennemyGroup[1] = new EnnemysubShuttle1(posX, posY + 30, world);
            ennemyGroup[2] = new EnnemysubShuttle1(posX, posY - 30, world);
            ennemyGroup[3] = new EnnemysubShuttle1(posX + 30, posY, world);
            ennemyGroup[4] = new EnnemysubShuttle1(posX + 30, posY + 30, world);
            ennemyGroup[5] = new EnnemysubShuttle1(posX + 30, posY - 30, world);
            ennemyGroup[6] = new EnnemysubShuttle1(posX + 60, posY, world);
            ennemyGroup[7] = new EnnemysubShuttle1(posX + 80, posY, world);
        }
    }

    @Override
    public void display(Graphics2D graphics) {
        if (ennemyGroup[0].isDead() == true) {
            die();
        }
        for (int i = 0; i < ennemyGroup.length; i++) {
            ennemyGroup[i].display(graphics);
        }
    }

    @Override
    public boolean die() {
        if (isDead() == false) {
            for (SpaceShuttle ennemyGroup1 : ennemyGroup) {
                ennemyGroup1.die();
            }
        }
        return super.die();
    }

    @Override
    public void applyLinearImpulse(Vec2 impulse, Vec2 point, Graphics2D graphics) {
        for (int i = 0; i < ennemyGroup.length; i++) {
            ennemyGroup[i].applyLinearImpulse(impulse, point, graphics); 
        }

    }

    @Override
    void applyAngularImpulse(float f) {
        for (int i = 0; i < ennemyGroup.length; i++) {
            ennemyGroup[i].applyAngularImpulse(f);
        }

    }

    @Override
    void applyForce(Vec2 vec2, Vec2 position) {
        for (int i = 0; i < ennemyGroup.length; i++) {
            ennemyGroup[i].applyForce(vec2, position);
        }
    }

    @Override
    public void fire(Graphics2D graphics, RocketType rocketType, float x, float y, Vec2 direction, CollisionCategory col) {
        for (int i = 0; i < ennemyGroup.length; i++) {
            ennemyGroup[i].fire(graphics, rocketType, x, y, direction, col); 
        }
    }

    @Override
    public void behave(Vec2 mainShuttlePos, Graphics2D graphics) {
        if (isDead() == false) {
            Vec2 vecDiff = mainShuttlePos.sub(ennemyGroup[0].getPosition());

            fire(graphics, RocketType.ROCKET,getX(), getY(), mainShuttlePos, CollisionCategory.WORLD);
            for (int i = 0; i < ennemyGroup.length; i++) {
                ennemyGroup[i].behave(mainShuttlePos, graphics);
                if (i >= 1) {
                    Vec2 vecDiffToMothership = ennemyGroup[0].getPosition().sub(ennemyGroup[i].getPosition());
                    float r = (float) Math.random() * 0.001f;
                    if (vecDiffToMothership.length() > 400) {
                        ennemyGroup[i].applyForce(vecDiffToMothership.mul(r).negate(), ennemyGroup[i].getPosition());
                        ennemyGroup[i].applyForce(vecDiffToMothership.mul(r).skew(), ennemyGroup[i].getPosition());
                    } else if (vecDiffToMothership.length() < 300) {
                        ennemyGroup[i].applyForce(vecDiffToMothership.mul(r), ennemyGroup[i].getPosition());
                        ennemyGroup[i].applyForce(vecDiffToMothership.mul(r).skew().negate(), ennemyGroup[i].getPosition());
                    }
                    ennemyGroup[i].applyForce(vecDiffToMothership.add(vecDiff).mul(r), ennemyGroup[i].getPosition());

                }
            }

        }

    }

}
