import java.util.*;
import src.main.java.Pieces;

class Settlement(){
    private List<Piece> pieces;
    private int sNum;
    private boolean hasTotoro;
    private boolean hasTiger;

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

    public Settlement(int sNum){
        this.sNum=sNum;
        this.hasTotoro=false;
        this.hasTiger=false;
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

    @Override
    public boolean equals(Object obj){
        if(!obj instanceorf Settlement)
            return false;
        Settlement other = (Settlement)obj;
        return sNum==other.sNum;
    }

    public List<Hex> findPieces(Hex[] hexes){
        List<Hex> occupied = new List<>();
        for(int i=0, i<hexes.length, i++){
            Piece temp = new Piece(1,hexes[i]);
            if(pieces.contains(temp){
                occupied.add(hexes[i]);
            }
            temp = null;
        }
        return occupied;
    }

    public void mergeSettlements(Settlement s1, Settlement s2){
        s1.addAll(s2);
        s2 = null;
    }
}
