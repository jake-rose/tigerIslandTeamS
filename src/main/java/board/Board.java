package board;

import board.Hex;
import board.HexManager;
import java.util.*;

public class Board{

    private HexManager hexManager;
    
    public HexManager getHexManager(){
        return hexManager;
    }
    public void setHexManager(HexManager hexManager){
        this.hexManager = hexManager;
    }
    //During initialization we should have the default tile
    public Board(){
        List<Hex> tile0 = new ArrayList();
        Hex hex0=new Hex(0,0,0,0,1);
        Hex hex1=new Hex(0,1,-1,0,2);
        Hex hex2=new Hex(1,0,-1,0,3);
        Hex hex3=new Hex(0,-1,1,0,4);
        Hex hex4=new Hex(-1,0,1,0,5);
        tile0.add(hex0);
        tile0.add(hex1);
        tile0.add(hex2);
        tile0.add(hex3);
        tile0.add(hex4);
        this.hexManager.setHexStack(tile0);
    }
    
    //Validate and place new tiles
    public void placeTile(Hex h1, Hex h2, Hex h3){
        hexManager.addHex(h1);
        hexManager.addHex(h2);
        hexManager.addHex(h3);
    }

    //Find adjacent terrain for sake of settlements
    public List<Hex> adjTerrain(Hex h1){
        List<Hex> adjHex = hexManager.findAdjPlaced(h1);
        for(Hex h: adjHex){
            if(!(h.getTerrain()==h1.getTerrain()))
                adjHex.remove(h);
        }
        return adjHex;
    }
    public List<Hex> adjTerrain(Hex h1, int terrain){
        List<Hex> adjHex = hexManager.findAdjPlaced(h1);
        for(Hex h: adjHex){
            if(!(h.getTerrain()==terrain))
                adjHex.remove(h);
        }
        return adjHex;
    }
}
