package fr.umlv.MasterPilot;


import java.awt.Graphics2D;
import java.awt.Shape;
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
public class EnnemyShuttle5 extends SpaceShuttle {

    SpaceShuttle[] ennemyGroup = new SpaceShuttle[16];
    Shape s;

    public EnnemyShuttle5(float x, float y, World world) {
        super(x, y);
        float posX = x;
        float posY = y;

        for (int i = 0; i < ennemyGroup.length; i++) {
            ennemyGroup[0] = new EnnemysubShuttle3(posX - 30, posY - 90, world);
            ennemyGroup[1] = new EnnemysubShuttle3(posX - 30, posY - 60, world);
            ennemyGroup[2] = new EnnemysubShuttle3(posX - 30, posY - 30, world);
            ennemyGroup[3] = new EnnemysubShuttle3(posX - 30, posY, world);
            ennemyGroup[4] = new EnnemysubShuttle3(posX - 30, posY + 30, world);
            ennemyGroup[5] = new EnnemysubShuttle3(posX - 30, posY + 60, world);
            ennemyGroup[6] = new EnnemysubShuttle3(posX - 30, posY + 90, world);

            ennemyGroup[7] = new EnnemysubShuttle3(posX, posY - 60, world);
            ennemyGroup[8] = new EnnemysubShuttle3(posX, posY - 30, world);
            ennemyGroup[9] = new EnnemysubShuttle3(posX, posY, world);
            ennemyGroup[10] = new EnnemysubShuttle3(posX, posY + 30, world);
            ennemyGroup[11] = new EnnemysubShuttle3(posX, posY + 60, world);
            ennemyGroup[12] = new EnnemysubShuttle3(posX + 30, posY - 30, world);
            ennemyGroup[13] = new EnnemysubShuttle3(posX + 30, posY, world);
            ennemyGroup[14] = new EnnemysubShuttle3(posX + 30, posY + 30, world);
            ennemyGroup[15] = new EnnemysubShuttle3(posX + 60, posY, world);

        }
    }

    @Override
    public void display(Graphics2D graphics) {
        boolean allDead = true;
        for (int i = 0; i < ennemyGroup.length; i++) {
            allDead = allDead && ennemyGroup[i].isDead();
        }
        if (allDead == true) {
            die();
        }
        for (int i = 0; i < ennemyGroup.length; i++) {
            ennemyGroup[i].display(graphics);
        }
    }

    @Override
    public boolean die() {
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
            ennemyGroup[i].fire(graphics, rocketType, x, y, direction, col); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void behave(Vec2 mainShuttlePos, Graphics2D graphics) {
        if (isDead() == false) {
            Vec2 vecDiff = mainShuttlePos.sub(ennemyGroup[0].getPosition());

            fire(graphics, RocketType.ROCKET, getX(), getY(), mainShuttlePos, CollisionCategory.WORLD);

            for (int i = 0; i < ennemyGroup.length; i++) {
                ennemyGroup[i].behave(mainShuttlePos, graphics);
                if (i >= 1) {
                    Vec2 vecDiffToMainshuttle = mainShuttlePos.sub(ennemyGroup[i].getPosition());
                    float r = (float) Math.random() * 0.001f;
                    if (vecDiffToMainshuttle.length() > 700) {
                        ennemyGroup[i].applyForce(vecDiffToMainshuttle.mul(r).negate(), ennemyGroup[i].getPosition());
                        ennemyGroup[i].applyForce(vecDiffToMainshuttle.mul(r).skew(), ennemyGroup[i].getPosition());
                    } else if (vecDiffToMainshuttle.length() < 300) {
                        ennemyGroup[i].applyForce(vecDiffToMainshuttle.mul(r), ennemyGroup[i].getPosition());
                        ennemyGroup[i].applyForce(vecDiffToMainshuttle.mul(r).skew().negate(), ennemyGroup[i].getPosition());
                    }

                }
            }

        }

    }

}
