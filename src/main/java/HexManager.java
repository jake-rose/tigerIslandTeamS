import Hex.java;
import java.util.List;

class HexManager(){
    private List<Hex> hexStack = new ArrayList<>();
    public Hex[] findAdj(Hex hex){
        int[] coord = hex.getCoord();
        Hex[] adjHex = new Hex[6];
        adjHex[0] = new Hex(coord[0]-1, coord[1], coord[2]);
        adjHex[1] = new Hex(coord[0], coord[1]-1, coord[2]);
        adjHex[2] = new Hex(coord[0], coord[1], coord[2]-1);
        adjHex[3] = new Hex(coord[0]+1, coord[1], coord[2]);
        adjHex[4] = new Hex(coord[0], coord[1]+1, coord[2]);
        adjHex[5] = new Hex(coord[0], coord[1], coord[2]+1);
        return adjHex;
    }
    public void addHex(Hex hex){
        this.hexStack.add(hex);
    }
    public void addHex(int x, int y, int z, int tile){
        Hex hex = new Hex(x,y,z);
        hex.setLevel(0);
        hex.setTile(tile);
        this.hexStack.add(hex);
    }
    public Hex findHex(Hex hex){
        if(hexStack.contains(hex))
            return hexStack.get(hexStack.indexOf(hex));
        else return void;
    }
    public void updateHex(Hex hex){
        Hex tempHex = this.hexStack.get(hexStack.indexOf(hex));
        this.hexStack.remove(hexStack.indexOf(hex));
        int level = tempHex.getLevel();
        int tile = hex.getTile();
        tempHex.setLevel(level+1);
        this.hexStack.add(tempHex);
    }
    public void updateHex(int x, int y, int z, int tile){
        Hex hex = new Hex(x,y,z);
        Hex tempHex = this.hexStack.get(hexStack.indexOf(hex));
        this.hexStack.remove(hexStack.indexOf(hex));
        int level = tempHex.getLevel();
        tempHex.setLevel(level+1);
        tmpeHex.setTile(tile);
        this.hexStack.add(tempHex);
    }

}
