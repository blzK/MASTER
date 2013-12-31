package fr.umlv.MasterPilot;


import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 * Generates Star objects around the SpaceShuttle
 *
 * @author azathoth
 */
public class Landscape implements Displayable {

    private final ArrayList<Displayable> displayableList = new ArrayList<>();
    private HashMap<Integer, Integer> positions = new HashMap<>();
    private float xMin;
    private float yMin;
    private float xMax;
    private float yMax;
    private final int planetRatio;
    private final int bonusRatio;

    public Landscape(MainShuttle mainShuttle, World world, int planetRatio, int bonusRatio) {
        float x = mainShuttle.getPosition().x;
        float y = mainShuttle.getPosition().y;
        this.xMin = (float) (x - MasterPilot.WIDTH * 1.5);
        this.yMin = (float) (y - MasterPilot.HEIGHT * 1.5);
        this.xMax = (float) (x + MasterPilot.WIDTH * 1.5);
        this.yMax = (float) (y + MasterPilot.HEIGHT * 1.5);
        this.planetRatio = planetRatio;
        this.bonusRatio = bonusRatio;
        positions = new HashMap<>();
        Random random = new Random(0);
        for (int i = 0; i < 100; i++) {
            positions.put((int) (random.nextInt((MasterPilot.WIDTH * 5)) + x - 2.5 * MasterPilot.WIDTH), (int) (random.nextInt((MasterPilot.HEIGHT * 5)) + y - 2.5 * MasterPilot.HEIGHT));
        }

        positions.keySet().iterator();
        Iterator itx = positions.keySet().iterator();
        Iterator ity = positions.values().iterator();

        int j = 0;
        while (itx.hasNext()) {
            int xPosition = (int) itx.next();
            int yPosition = (int) ity.next();

            if (j < planetRatio) {
                Planet planet = new Planet(world, xPosition + 10, yPosition + 10);
                displayableList.add(planet);
            } else if (j < planetRatio + bonusRatio) {
                double n =  Math.round(Math.random());
                if (n == 1) {
                    Bonus bonus1 = new Bonus(world, xPosition, yPosition, RocketType.ExpBomb);
                    displayableList.add(bonus1);
                }
                if (n == 0) {
                    Bonus bonus2 = new Bonus(world, xPosition, yPosition, RocketType.ImpBomb);
                    displayableList.add(bonus2);
                }

            } else {
                Star star = new Star(xPosition, yPosition);
                displayableList.add(star);

            }
            j++;
        }
    }

    /**
     * Tests if the
     *
     * @param spaceShuttle the shuttle taken as a reference point.
     * @return true if the shuttle is within the defined parameter, stars have
     * been generated for the area. false if the shuttle is out of the area, we
     * then need to generate stars.
     */
    public boolean isInside(SpaceShuttle spaceShuttle) {
        if (spaceShuttle.getPosition().x >= xMin && spaceShuttle.getPosition().x <= xMax && spaceShuttle.getPosition().y >= yMin && spaceShuttle.getPosition().y <= yMax) {
            return true;
        }
        return false;
    }

    public void display(Graphics2D graphics, MainShuttle mainShuttle) {
        for (Displayable sprite : displayableList) {
            sprite.display(graphics, mainShuttle);
        }
    }

    public void generateLandscape(MainShuttle mainShuttle, World world, int planetRatio, int bonusRatio) {
        float x = mainShuttle.getPosition().x;
        float y = mainShuttle.getPosition().y;
        this.xMin = (float) (x - MasterPilot.WIDTH * 1.5);
        this.yMin = (float) (y - MasterPilot.HEIGHT * 1.5);
        this.xMax = (float) (x + MasterPilot.WIDTH * 1.5);
        this.yMax = (float) (y + MasterPilot.HEIGHT * 1.5);
        positions = new HashMap<>();
        Random random = new Random(0);

        // EN HAUT
        for (int i = 0; i < 50; i++) {
            positions.put((int) (random.nextInt((int) (MasterPilot.WIDTH * 5)) + x), (int) (random.nextInt((int) (MasterPilot.HEIGHT * 5)) + y - 6 * MasterPilot.HEIGHT));
        }
        // BAS
        for (int i = 0; i < 50; i++) {
            positions.put((int) (random.nextInt((int) (MasterPilot.WIDTH * 5)) + x), (int) (random.nextInt((int) (MasterPilot.HEIGHT * 5)) + y + 2 * MasterPilot.HEIGHT));
        }

        //A DROITE
        for (int i = 0; i < 50; i++) {
            positions.put((int) (random.nextInt((int) (MasterPilot.WIDTH * 5)) + x + 2 * MasterPilot.WIDTH), (int) (random.nextInt((int) (MasterPilot.HEIGHT * 5)) + y));
        }

        //A GAUCHE
        for (int i = 0; i < 50; i++) {
            positions.put((int) (random.nextInt((int) (MasterPilot.WIDTH * 5)) + x - 6 * MasterPilot.WIDTH), (int) (random.nextInt((int) (MasterPilot.HEIGHT * 5)) + y));
        }

        positions.keySet().iterator();
        Iterator itx = positions.keySet().iterator();
        Iterator ity = positions.values().iterator();
        int j = 0;
        while (itx.hasNext()) {
            int xPosition = (int) itx.next();
            int yPosition = (int) ity.next();

            if (j < planetRatio) {
                Planet planet = new Planet(world, xPosition + 10, yPosition + 10);
                displayableList.add(planet);
            } else if (j < planetRatio + bonusRatio) {
                double n =  Math.round(Math.random());
                if (n == 0) {
                    Bonus bonus1 = new Bonus(world, xPosition, yPosition, RocketType.ExpBomb);
                    displayableList.add(bonus1);
                }
                if (n == 1) {
                    Bonus bonus2 = new Bonus(world,xPosition, yPosition, RocketType.ImpBomb);
                    displayableList.add(bonus2);
                }

            } else {
                Star star = new Star(xPosition, yPosition);
                displayableList.add(star);

            }
            j++;
        }
    }

    @Override
    public void display(Graphics2D graphics) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   


}
