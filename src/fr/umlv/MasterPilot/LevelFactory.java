package fr.umlv.MasterPilot;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 * LevelFacotry is a class in order to create level since XML data.
 * 
 * It extract data of XML file and put its in a hashmap.
 */
public class LevelFactory {
    
     /**
     * loadLevel is a method who call loadXML(path) (a private method), and recover a hashmap of loadXML()
     * 
     * @param level A string designating the level.
     * @return dataLevel A Hashmap where the key is PlanetDensity and associate of its pourcentage for exemple.
     * @throws javax.xml.parsers.ParserConfigurationException parser exception.
     * @throws org.xml.sax.SAXException xml error.
     * @throws java.io.IOException File error.
     */
    public static HashMap<String,Integer> loadLevel(String level) throws ParserConfigurationException, SAXException, IOException{
        
        Path path = Paths.get("src/xml/"+level+".xml");
        HashMap dataLevel = loadXML(path);
        
        return dataLevel;
    }
    
    private static HashMap<String,Integer> loadXML(Path path) throws ParserConfigurationException, SAXException, IOException {
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder(); 
        Document doc = db.parse(path.toFile());
        
        HashMap<String,Integer> donneesXML = new HashMap<>();
                
                
        Node root;
        ArrayList<Node> listNodes;
        ArrayList<Node> listWaves;
        ArrayList<Node> listDensity;
        ArrayList<Node> listWavesEnnemy;
        
        String tmp;
        root = doc.getFirstChild();
        
        listNodes = properNodeList(root);
        
        for(int i=0;i<listNodes.size();i++){
            
            if(listNodes.get(i).getNodeName().equals("Wave")){
                String s = listNodes.get(i).getAttributes().getNamedItem("nbWave").getNodeValue();
                Integer nbWave = Integer.parseInt(s);
                donneesXML.put("nbWave", nbWave);
                listWaves = properNodeList(listNodes.get(i));
                
                for(int j=0;j<listWaves.size();j++){
                    listWavesEnnemy = properNodeList(listWaves.get(j));
                    // Describe Waves
                    for(int k=0;k<listWavesEnnemy.size();k++){
                        switch(listWavesEnnemy.get(k).getNodeName()){
                            case "Tie":
                                        tmp = listWavesEnnemy.get(k).getAttributes().getNamedItem("value").getNodeValue();
                                        Integer nbTie = Integer.parseInt(tmp);
                                        donneesXML.put("Wave"+(j+1)+"_Tie", nbTie);
                            break;
                            case "Cruiser":
                                        tmp = listWavesEnnemy.get(k).getAttributes().getNamedItem("value").getNodeValue();
                                        Integer nbCruiser = Integer.parseInt(tmp);
                                        donneesXML.put("Wave"+(j+1)+"_Cruiser", nbCruiser);
                            break;
                            case "Squadron":
                                        tmp = listWavesEnnemy.get(k).getAttributes().getNamedItem("value").getNodeValue();
                                        Integer nbSquadron = Integer.parseInt(tmp);
                                        donneesXML.put("Wave"+(j+1)+"_Squadron", nbSquadron);
                            break;
                            case "FakePlanet":
                                        tmp = listWavesEnnemy.get(k).getAttributes().getNamedItem("value").getNodeValue();
                                        Integer nbFakePlanet = Integer.parseInt(tmp);
                                        donneesXML.put("Wave"+(j+1)+"_FakePlanet", nbFakePlanet);
                            break;
                            case "Soon":
                                        tmp = listWavesEnnemy.get(k).getAttributes().getNamedItem("value").getNodeValue();
                                        Integer nbSoon = Integer.parseInt(tmp);
                                        donneesXML.put("Wave"+(j+1)+"_Soon", nbSoon);
                            break;
                        }
                    }
                }
            }
            else if(listNodes.get(i).getNodeName().equals("World")){
                listDensity = properNodeList(listNodes.get(i));
                for(i=0;i<listDensity.size();i++){
                    if(listDensity.get(i).getNodeName().equals("PlanetDensity")){
                        tmp = listDensity.get(i).getAttributes().getNamedItem("value").getNodeValue();
                        Integer planetDensity = Integer.parseInt(tmp);
                        donneesXML.put(listDensity.get(i).getNodeName(), planetDensity);
                    }
                    else if(listDensity.get(i).getNodeName().equals("BonusDensity")){
                        tmp = listDensity.get(i).getAttributes().getNamedItem("value").getNodeValue();
                        Integer bonusDensity = Integer.parseInt(tmp);
                        donneesXML.put(listDensity.get(i).getNodeName(), bonusDensity);
                    }
                }
            }
        }
        return donneesXML;
        
        
    }
    
    // Proper because without this method, you have a "#text" node
    static private ArrayList<Node> properNodeList(Node node){
        ArrayList<Node> listNodes = new ArrayList<Node>();
        NodeList nodelist = node.getChildNodes();
        for(int i =0;i<nodelist.getLength();i++){
            if(!nodelist.item(i).getNodeName().equals("#text")){
                listNodes.add(nodelist.item(i));
            }
        }
        return listNodes;
    }
    
}
