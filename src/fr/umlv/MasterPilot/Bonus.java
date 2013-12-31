package fr.umlv.MasterPilot;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import org.jbox2d.collision.shapes.CircleShape;
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
public class Bonus extends FlyingObject {

    private final RocketType type;

    public Bonus(World world, float x, float y, RocketType type) {
        super(x, y);

        this.type = type;
        BodyDef bodyDefBonus = new BodyDef();
        bodyDefBonus.position.set(x, y);
        bodyDefBonus.type = BodyType.STATIC;
        setBody(world.createBody(bodyDefBonus));

        FixtureDef fdBonus = new FixtureDef();
        CircleShape cs = new CircleShape();
        cs.m_radius = 30f;
        fdBonus.filter.categoryBits = CollisionCategory.BONUS.getBits();
        fdBonus.filter.maskBits = CollisionCategory.PLAYER.getBits();
        fdBonus.shape = cs;
        fdBonus.density = 1f;
        fdBonus.restitution = 1f;
        fdBonus.friction = 1.0f;
        getBody().createFixture(fdBonus);
        getBody().setUserData("Bonus");
    }

    @Override
    public void display(Graphics2D graphics, MainShuttle mainShuttle) {
        if (getBody().getContactList() != null) {
            System.out.println(getBody().getContactList());
            powerUp(mainShuttle);
            die();
        }
        if (isDead() == false) {
            float posX = MasterPilot.toXCoordinates(getBody().getPosition().x);
            float posY = MasterPilot.toYCoordinates(getBody().getPosition().y);
            float x1Points[] = {posX - 10, posX - 5, posX + 5, posX + 10, posX + 5, posX - 5};
            float y1Points[] = {posY, posY + 5, posY + 5, posY, posY - 5, posY - 5};
            GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x1Points.length);
            polygon.moveTo(x1Points[0], y1Points[0]);

            for (int index = 1; index < x1Points.length; index++) {
                polygon.lineTo(x1Points[index], y1Points[index]);
            }

            polygon.closePath();
            if (type == RocketType.ExpBomb) {
                graphics.setColor(Color.RED);
            } else if (type == RocketType.ImpBomb) {
                graphics.setColor(Color.ORANGE);
            }
            graphics.fill(polygon);

        }
    }

    @Override
    public boolean die() {
        return super.die(); //To change body of generated methods, choose Tools | Templates.
    }

    private void powerUp(MainShuttle mainShuttle) {
        mainShuttle.setBonus(this.type);
    }

    @Override
    public void display(Graphics2D graphics) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
