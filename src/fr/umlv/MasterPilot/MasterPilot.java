package fr.umlv.MasterPilot;

import java.awt.Color;
import fr.umlv.zen3.Application;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.xml.sax.SAXException;

public class MasterPilot {

    final static int WIDTH = 800;
    final static int HEIGHT = 600;

    public static float toXCoordinates(float x) {
        return x + 372;
    }

    public static float toYCoordinates(float y) {
        return y + 270;
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        //WORLD
        World world = new World(new Vec2(0, 0));
        float timeStep = 1.0f / 60.f;
        int velocityIterations = 6;
        int positionIterations = 2;
        //MAIN SHUTLE
        ShuttleFactory shuttleFactory = new ShuttleFactory();
        MainShuttle mainShuttle = new MainShuttle(0f, 0f, world);
        //LANDSCAPE AND ENNEMY GENERATION
        EnnemyGeneration ennemyGeneration = new EnnemyGeneration();
        Ennemies ennemies = new Ennemies(0, 0);

        Landscape landscape = new Landscape(mainShuttle, world, 20, 30);

//        WINDOW
        Menu menu = new Menu();

        Application.run("MasterPilot", WIDTH, HEIGHT, context -> {

            for (;;) {
                try {
                    Thread.sleep(4);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                context.render(graphics -> {

                    HashMap<String, Integer> menuChoice;
                    menuChoice = menu.getChoice();
                    if (menu.getCompteur() == 0) {
                        menuChoice = menu.launch(graphics, WIDTH, HEIGHT, context.pollKeyboard());
                    } else {

                        //BACKGROUND
                        graphics.setColor(Color.black);
                        graphics.fill(new Rectangle2D.Float(0, 0, WIDTH, HEIGHT));
                        int j = 0;
                        float x = mainShuttle.getPosition().x;
                        float y = mainShuttle.getPosition().y;
                        // CENTERVIEW
                        graphics.translate(-x, -y);
                        graphics.fill(new Rectangle2D.Float(0, 0, WIDTH, HEIGHT));
                        // LANSCAPE                
                        if (!landscape.isInside(mainShuttle)) {
                            landscape.generateLandscape(mainShuttle, world, 20, 30);
                        }

                        landscape.display(graphics, mainShuttle);

                        //ENNEMY SHUTTLE 
                        if (ennemyGeneration.isGenerated() == false) {
                            System.out.println(ennemyGeneration.isGenerated());
                            try {
                                ennemies.setEnnemies(ennemyGeneration.Generate("1", world, LevelFactory.loadLevel("level" + menuChoice.get("level"))));
                                System.out.println(ennemies);
                            } catch (ParserConfigurationException | SAXException | IOException e) {
                                System.err.println("Error load XML");
                            }
                        }

                        if (!ennemies.isEmpty()) {
                            ennemies.display(graphics, mainShuttle);
                            ennemies.behave(mainShuttle.getPosition(), graphics);
                        }
                        //                   
                        //MAIN SPACESHUTTLE
                        mainShuttle.display(graphics);

                        //DISPOSE AND STEP TIME
                        graphics.dispose();

                        //KEYBOARD CONTROL
                        ShuttleControl.move(mainShuttle, context.pollKeyboard(), graphics);

                        //DISPOSE BODIES
                        Body bodyTemp = world.getBodyList().getNext();
                        Body bodyTemp2 = world.getBodyList().getNext();
                        for (int i = 0; i < world.getBodyCount() - 1; i++) {

                            if (bodyTemp != null && Math.abs(bodyTemp.getPosition().x - mainShuttle.getPosition().x) > WIDTH * 7 && Math.abs(bodyTemp.getPosition().y - mainShuttle.getPosition().y) > HEIGHT * 7) {
                                world.destroyBody(bodyTemp2);
                            }
                            bodyTemp = bodyTemp.getNext();
                            bodyTemp2 = bodyTemp;
                        }
                        world.step(timeStep, velocityIterations, positionIterations);
                    }
                });
            }
        });
    }
}
