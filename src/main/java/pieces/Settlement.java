import java.util.*;
import src.main.java.Pieces;

class Settlement(){
    private List<Piece> pieces;
    private int sNum;
    private boolean hasTotoro;
    private boolean hasTiger;

    public Settlement(List<Piece> pieces){
        this.pieces=pieces;
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

    public void mergeSettlements(Settlement s1, Settlement s2){
        s1.addAll(s2);
        s2 = null;
    }
}
