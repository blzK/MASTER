package fr.umlv.MasterPilot;


import com.sun.webkit.dom.KeyboardEventImpl;
import fr.umlv.zen3.KeyboardEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Anthony
 */
public class Menu {
    
    private int compteur;
    private  HashMap<String,Integer> menuChoice ;
    
    public Menu(){
        this.compteur =0;
        this.menuChoice = new HashMap<>();
    }
    
    public HashMap<String, Integer> launch(Graphics2D graphics, int width, int height, KeyboardEvent event){
            
        
        graphics.setColor(Color.darkGray);
        graphics.fill(new Rectangle2D.Float(0, 0, width, height));
        graphics.setColor(Color.white);
        graphics.setFont(new Font("Verdana", Font.BOLD, 40));
        graphics.drawString("MasterPilot", width/2-125, height/6);
        graphics.setFont(new Font("Verdana", Font.BOLD, 20));
        if(this.menuChoice.get("firstChoice") == null){            
            graphics.drawString("N - New Game", width/2-100, height/2);
            graphics.drawString("Q - Exit Game", width/2-100, height/2+50);
        }
        else if(this.menuChoice.get("level") == null){            
            graphics.drawString("A - Level 1", width/2-100, height/2);
            graphics.drawString("B - Level 2", width/2-100, height/2+50);
            graphics.drawString("C - Level 3", width/2-100, height/2+100);
        }
        else if(this.menuChoice.get("mode") == null){            
            graphics.drawString("E - Easy mode", width/2-100, height/2);
            graphics.drawString("H - Hardcore mode", width/2-100, height/2+50);
        }
        if(event != null){
            switch(event.getKey()){
                case N: this.menuChoice.put("firstChoice", 1);
                break;
                case Q:
                break;
                case A: this.menuChoice.put("level", 1);
                break;
                case B: this.menuChoice.put("level", 2);
                break;
                case C: this.menuChoice.put("level", 3);
                break;
                case E: this.menuChoice.put("mode", 0);
                        this.compteur = 1;
                break;
                case H: this.menuChoice.put("mode", 1);
                        this.compteur = 1;
                break;
                default:
                        graphics.setColor(Color.red);
                        graphics.setFont(new Font("Verdana", Font.ITALIC, 20));
                        graphics.drawString("Invalid choice", width/2-100, height/2+200);
                break;
            }
            graphics.setColor(Color.white);
            graphics.setFont(new Font("Verdana", Font.BOLD, 20));
        }
        
        return menuChoice;
    }
    
    public int getCompteur(){
        return this.compteur;
    }
    
    public HashMap<String, Integer> getChoice(){
        return this.menuChoice;
    }
}
