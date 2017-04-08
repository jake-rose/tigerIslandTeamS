import java.util.*;
import board;

class Piece(){
    //0=meeple, 1=totoro, 2=tiger
    private int type;
    private Hex location;

    public void setLocation(Hex location){
        this.location=location;
    }
    public Hex getLocation(){
        return this.location;
    }

    public void setType(int type){
        this.type=type;
    }
    public int getType(){
        return this.type;
    }
    
    public Piece(int type, Hex hex){
        this.type = type;
        this.hex = hex;
    }

    public Piece(int type, int x, int y, int z){
        this.type = type;
        Hex tempHex = new Hex(x,y,z);
        this.location=tempHex;
    }

    @Override
    public boolean equals(Object obj){//Override equals method for sake of contains() method
        if(!obj instanceof Piece) 
            return false;
        Piece other = (Piece)obj;
        //Only examine pieces using the coordinates
        return location.getCoord()=other.location.getCoord();
    }
}
