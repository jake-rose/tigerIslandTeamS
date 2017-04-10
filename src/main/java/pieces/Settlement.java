package pieces;

import java.util.*;

import pieces.*;
import board.*;

public class Settlement{
    private List<Piece> pieces;
    private int sNum;
    private boolean hasTotoro;
    private boolean hasTiger;

    //Constrcutors for both lists and individual pieces
    public Settlement(int sNum, List<Piece> pieces){
        this.pieces=pieces;
        this.hasTotoro=false;
        this.hasTiger=false;
    }
    public Settlement(int sNum, Piece piece){
        this.pieces.add(piece);
        this.sNum=sNum;
        this.hasTotoro=false;
        this.hasTiger=false;
    }

    //Skeleton constructor for temp settlements
    public Settlement(int sNum){
        this.sNum=sNum;
        this.hasTotoro=false;
        this.hasTiger=false;
    }
    //default constructor
    public Settlement(){
        this.pieces=null;
        this.hasTotoro=false;
        this.hasTiger=false;
        this.sNum=-1;
    }
    
    public List<Piece> getPieces(){
        return this.pieces;
    }
    public void addPiece(Piece piece){
        this.pieces.add(piece);
    }

    public int getSNum(){
        return this.sNum;
    }
    public void setSNum(int sNum){
        this.sNum=sNum;
    }
    //create a size function that return the size of pieces
    public int size(){return pieces.size();}

    public boolean getTotoro(){
        return this.hasTotoro;
    }
    public void setTotoro(boolean hasTotoro){
        this.hasTotoro=hasTotoro;
    }

    public boolean getTiger(){
        return this.hasTiger;
    }
    public void setTiger(boolean hasTiger){
        this.hasTiger=hasTiger;
    }

    public boolean findPiece(Hex location){
        Piece temp = new Piece(1,location);
        return pieces.contains(temp);
    }

    //Replace equals so as to only compare sNum
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Settlement))
            return false;
        Settlement other = (Settlement)obj;
        return sNum==other.sNum;
    }
    
    //Find pieces using either list of hexes or array
    public List<Hex> findPieces(List<Hex> hexes){
        ArrayList<Hex> occupied = new ArrayList();
        for(Hex h: hexes){
            Piece temp = new Piece(1,h);
            if(pieces.contains(temp)){
                occupied.add(h);
            }
            temp = null;
        }
        return occupied;
    }

    public List<Hex> findPieces(Hex[] hexes){
        ArrayList<Hex> occupied = new ArrayList();
        for(int i=0; i<hexes.length; i++){
            Piece temp = new Piece(1,hexes[i]);
            if(pieces.contains(temp)){
                occupied.add(hexes[i]);
            }
            temp = null;
        }
        return occupied;
    }

    public void mergeSettlements(Settlement s1, Settlement s2){
        s1.getPieces().addAll(s2.getPieces());
        s2 = null;
    }

    public void addPieces(List<Piece> pieces){
        this.pieces.addAll(pieces);
    }
}
