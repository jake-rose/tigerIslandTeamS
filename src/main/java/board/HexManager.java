package board;

import java.util.ArrayList;
import java.util.List;

public class HexManager{
    private static List<Hex> hexStack = new ArrayList();
    private static List<Hex> hexOccupied = new ArrayList();
    public List<Hex> getHexStack(){
        return this.hexStack;
    }
    public void setHexStack(List<Hex> stack){
        this.hexStack = stack;
    }

    public List<Hex> getHexOccupied(){
        return this.hexOccupied;
    }
    public void setHexOccupied(List<Hex> stack){
        this.hexOccupied = stack;
    }
    //HexManager Constructor
    public HexManager(List<Hex> hexStack){
        this.hexStack = hexStack;
    }
    
    //Gets array of adjacent coordinates
    public static Hex[] findAdj(Hex hex){
        int[] coord = hex.getCoord();
        Hex[] adjHex = new Hex[6];
        adjHex[0] = new Hex(coord[0]-1, coord[1]+1, coord[2]);
        adjHex[1] = new Hex(coord[0], coord[1]+1, coord[2]-1);
        adjHex[2] = new Hex(coord[0]+1, coord[1], coord[2]-1);
        adjHex[3] = new Hex(coord[0]+1, coord[1]-1, coord[2]);
        adjHex[4] = new Hex(coord[0], coord[1]-1, coord[2]+1);
        adjHex[5] = new Hex(coord[0]-1, coord[1], coord[2]+1);
        return adjHex;
    }
    //Gets list of existing adjacent hexes
    public static List<Hex> findAdjPlaced(Hex hex){
        List<Hex> hexes = new ArrayList();
        Hex[] adjHex = findAdj(hex);
        for(int i=0; i<adjHex.length;i++){
            if(hexStack.contains(adjHex[i]))
                hexes.add(hexStack.get(hexStack.indexOf(adjHex[i])));
        }
        return hexes;
    }

    //Add full hex
    public void addHex(Hex hex){
        this.hexStack.add(hex);
    }
    public void addOccupied(Hex hex){
        this.hexStack.remove(this.hexStack.indexOf(hex));
        this.hexOccupied.add(hex);
    }
    //Add hex based on input
    public void addHex(int x, int y, int z, int tile, int terrain){
        Hex hex = new Hex(x,y,z);
        hex.setLevel(1);
        hex.setTile(tile);
        hex.setTerrain(terrain);
        this.hexStack.add(hex);
    }
    public void addOccupied(int x, int y, int z, int tile, int terrain){
        Hex hex = new Hex(x,y,z);
        hex.setLevel(1);
        hex.setTile(tile);
        hex.setTerrain(terrain);
        this.hexStack.remove(this.hexStack.indexOf(hex));
        this.hexOccupied.add(hex);
    }

    //Finds hex based on coordinates
    public Hex findHex(Hex hex){
        if(hexStack.contains(hex))
            return hexStack.get(hexStack.indexOf(hex));
        else if(hexOccupied.contains(hex))
            return hexOccupied.get(hexOccupied.indexOf(hex));
        else 
            return null;
    }

    //Find hex in stack and replace with updated information
    public void updateHex(Hex hex){
        if(this.hexStack.contains(hex)){
            Hex tempHex = this.hexStack.get(hexStack.indexOf(hex));
            this.hexStack.remove(hexStack.indexOf(hex));
            int level = tempHex.getLevel();
            tempHex.setTile(hex.getTile());
            tempHex.setLevel(level+1);
            tempHex.setTerrain(hex.getTerrain());
            this.hexStack.add(tempHex);
        }
        else{
            Hex tempHex = this.hexOccupied.get(hexOccupied.indexOf(hex));
            this.hexOccupied.remove(hexStack.indexof(hex));
            int level = tempHex.getLevel();
            tempHex.setTile(hex.getTile());
            tempHex.setLevel(level+1);
            tempHex.setTerrain(hex.getTerrain());
            this.hexStack.add(tempHex);
        }
    }
    //Update hex based on coordinate input & update with new tile, level, and terrain
    public void updateHex(int x, int y, int z, int tile, int terrain){
        Hex hex = new Hex(x,y,z);
        if(this.hexStack.contains(hex)){
            Hex tempHex = this.hexStack.get(hexStack.indexOf(hex));
            this.hexStack.remove(hexStack.indexOf(hex));
            int level = tempHex.getLevel();
            tempHex.setTile(tile);
            tempHex.setLevel(level+1);
            tempHex.setTerrain(terrain);
            this.hexStack.add(tempHex);
        }
        else{
            Hex tempHex = this.hexOccupied.get(hexOccupied.indexOf(hex));
            this.hexOccupied.remove(hexStack.indexof(hex));
            int level = tempHex.getLevel();
            tempHex.setTile(tile);
            tempHex.setLevel(level+1);
            tempHex.setTerrain(terrain);
            this.hexStack.add(tempHex);
        }
    }
    //Check if two hexes are same level
    private boolean sameLevel(Hex hex1, Hex hex2){
        return hex1.getLevel()==hex2.getLevel();
    }
    private boolean sameTile(Hex hex1, Hex hex2){
        return hex1.getTile()==hex2.getTile();
    }
    //Check that all hexes of tile are at same level & volcano is on volcano & not covering only 1 tile
    //another idea:Check that all hexes of tile are at same level & volcano is on volcano & have different orientation
    public boolean validTileHexes(Hex hex1, Hex hex2, Hex hex3){
        return sameLevel(hex1,hex2) && sameLevel(hex2,hex3) && sameLevel(hex3,hex1) && hex1.getTerrain()==1 && !(sameTile(hex1,hex2) && sameTile(hex2,hex3) && sameTile(hex1,hex3));
    }
    //Check if two hexes have the same terrain
    public boolean sameTerrain(Hex hex1, Hex hex2){
        return hex1.getTerrain()==hex2.getTerrain();
    }
}
