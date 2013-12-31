package fr.umlv.MasterPilot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.jbox2d.common.Timer;
import org.jbox2d.dynamics.World;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Anthony
 */
public class EnnemyGeneration {

    private ArrayList<SpaceShuttle> listEnnemy;
    private HashMap<String, Integer> dataLevel;
    private World world;
    private Timer timer;
    private boolean isGenerated;

    public EnnemyGeneration() {//World world, HashMap<String,Integer> dataLevel) {
        isGenerated = false;
    }

    public ArrayList<SpaceShuttle> Generate(String vague, World world, HashMap<String, Integer> dataLevel) {
        this.listEnnemy = new ArrayList<>();
        this.world = world;
        this.dataLevel = dataLevel;
        this.timer = new Timer();

        this.listEnnemy = new ArrayList<>();

        Iterator itkey = this.dataLevel.keySet().iterator();
        Iterator itval = this.dataLevel.values().iterator();

        int i;
        while (itkey.hasNext()) {
            String key = itkey.next().toString();
            int val = (int) itval.next();
            // Keep only shuttle of our wave
            if (key.contains("Wave" + vague)) {
                if (key.contains("Tie")) {
                    for (i = 0; i < val; i++) {
                        this.listEnnemy.add(new EnnemyShuttle1(-200, 0, world));
                    }
                } else if (key.contains("Cruiser")) {
                    for (i = 0; i < val; i++) {
                        this.listEnnemy.add(new EnnemyShuttle2(-400, 0, world));
                    }
                } else if (key.contains("Squadron")) {
                    for (i = 0; i < val; i++) {
                        this.listEnnemy.add(new EnnemyShuttle3(0, 200, world));
                    }
                } else if (key.contains("FakePlanet")) {
                    for (i = 0; i < val; i++) {
                        this.listEnnemy.add(new EnnemyShuttle4(600, 600, world));
                    }
                } else { // 5 type
                    for (i = 0; i < val; i++) {
                        this.listEnnemy.add(new EnnemyShuttle5(1000, 0, world));
                    }
                }
            }
            isGenerated = true;
        }

        return this.listEnnemy;
    }

    public boolean isGenerated() {
        return isGenerated;
    }

    public Timer getTimer() {
        return this.timer;
    }

}
