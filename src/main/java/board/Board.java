import src.main.java.board;
import java.util.*;

class Board(){
    
    private HexManager hexManager;
    //During initialization we should have the default tile
    public Board(){
        List<Hex> tile0 = new ArrayList<>();
        tile0.add(Hex hex0=new Hex(0,0,0,0,1));
        tile0.add(Hex hex1=new Hex(0,1,-1,0,2));
        tile0.add(Hex hex2=new Hex(1,0,-1,0,3));
        tile0.add(Hex hex3=new Hex(0,-1,1,0,4));
        tile0.add(Hex hex4=new Hex(-1,0,1,0,5));
        this.hexManager.setHexStack(tile0);
    }
    
    //Validate and place new tiles
    public boolean placeTile(Hex h1, Hex h2, Hex h3){
        if(hexManager.validTileHexes(h1,h2,h3)){
            hexManager.addHex(h1);
            hexManager.addHex(h2);
            hexManager.addHex(h3);
            return true;
        }
        else
            return false;
    }

    //Find adjacent terrain for sake of settlements
    public List<Hex> adjTerrain(Hex h1){
        List<Hex> adjHex = hexManager.findAdjPlaced(h1);
        for(Hex h: adjHex)
            if(!h.getTerrain()==h1.getTerrain())
                adjHex.remove(h);
        return adjHex;
    }
}
