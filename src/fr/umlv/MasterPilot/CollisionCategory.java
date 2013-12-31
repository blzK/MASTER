package fr.umlv.MasterPilot;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author azathoth
 */
public enum CollisionCategory {
    PLAYER(0x0001), WORLD(0x0002 ),ENNEMY(0x0004), BONUS(0x0008);
    
        private CollisionCategory(int b) {
        this.b=b;
    }
        public int getBits() {
        return this.b;
    }
    final private int b;
}
