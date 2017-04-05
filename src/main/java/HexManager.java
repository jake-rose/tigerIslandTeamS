import Hex.java;
import java.util.List;

class HexManager(){
    private List<Hex> hexStack = new ArrayList<>();
    public List<Hex> getHexStack(){
        return this.hexStack;
    }
    public void setHexStack(List<Hex> stack){
        this.hexStack = stack;
    }
    //HexManager Constructor
    public HexManager(List<Hex> hexStack){
        this.hexStack = hexStack;
    }
    
    //Get list of adjacent coordinates
    public Hex[] findAdj(Hex hex){
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

    //Add full hex
    public void addHex(Hex hex){
        this.hexStack.add(hex);
    }
    //Add hex based on input
    public void addHex(int x, int y, int z, int tile, int terrain){
        Hex hex = new Hex(x,y,z);
        hex.setLevel(1);
        hex.setTile(tile);
        hex.setTerrain(terrain);
        this.hexStack.add(hex);
    }

    //Finds hex based on coordinates
    public Hex findHex(Hex hex){
        if(hexStack.contains(hex))
            return hexStack.get(hexStack.indexOf(hex));
        else return void;
    }

    //Find hex in stack and replace with updated information
    public void updateHex(Hex hex){
        Hex tempHex = this.hexStack.get(hexStack.indexOf(hex));
        this.hexStack.remove(hexStack.indexOf(hex));
        int level = tempHex.getLevel();
        tempHex.setTile(hex.getTile());
        tempHex.setLevel(level+1);
        tempHex.setTerrain(hex.getTerrain());
        this.hexStack.add(tempHex);
    }
    //Update hex based on coordinate input & update with new tile, level, and terrain
    public void updateHex(int x, int y, int z, int tile, int terrain){
        Hex hex = new Hex(x,y,z);
        Hex tempHex = this.hexStack.get(hexStack.indexOf(hex));
        this.hexStack.remove(hexStack.indexOf(hex));
        int level = tempHex.getLevel();
        tempHex.setLevel(level+1);
        tempHex.setTile(tile);
        tempHex.setTerrain(terrain);
        this.hexStack.add(tempHex);
    }
    //Check if two hexes are same level
    private boolean sameLevel(Hex hex1, Hex hex2){
        return hex1.getLevel()==hex2.getLevel();
    }
    private boolean sameTile(Hex hex1, Hex hex2){
        return hex1.getTile()==hex2.getTile();
    }
    //Check that all hexes of tile are at same level & volcano is on volcano & not covering only 1 tile
    public boolean validTileHexes(Hex hex1, Hex hex2, Hex hex3){
        return sameLevel(hex1,hex2) && sameLevel(hex2,hex3) && sameLevel(hex3,hex1) && hex1.getTerrain()==1 && !(sameTile(hex1,hex2) && sameTile(hex2,hex3) && sameTile(hex1,hex3));
    }
    //Check if two hexes have the same terrain
    public boolean sameTerrain(Hex hex1, Hex hex2){
        return hex1.getTerrain()==hex2.getTerrain();
    }
}
