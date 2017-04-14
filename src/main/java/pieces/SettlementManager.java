package pieces;

import board.*;
import pieces.*;
import java.util.*;

public class SettlementManager{
    private List<Settlement> settlements;
    private int sNumIterator=0;
    private int totoros=3;
    private int tigers=2;
    private int meeples=20;

    public void newSettlement(Piece piece, int usedMeeples){
        settlements.add(new Settlement(sNumIterator,piece));
        this.meeples=this.meeples-usedMeeples;
        sNumIterator++;
    }

    public int getMeeples(){
        return this.meeples;
    }
    public void setMeeples(int meeples){
        this.meeples = meeples;
    }

    public int getTotoros(){
        return this.totoros;
    }
    public int getTigers(){
        return this.tigers;
    }

    public void expandSettlement(Piece piece, int sNum){
        settlements.get(settlements.indexOf(new Settlement(sNum))).addPiece(piece);
    }

    public void addTotoroToSettlement(Piece piece, int sNum){
        settlements.get(settlements.indexOf(new Settlement(sNum))).addPiece(piece);
        settlements.get(settlements.indexOf(new Settlement(sNum))).setTotoro(true);
    }

    public void addTigerToSettlement(Piece piece, int sNum){
        settlements.get(settlements.indexOf(new Settlement(sNum))).addPiece(piece);
        settlements.get(settlements.indexOf(new Settlement(sNum))).setTiger(true);
    }

    public List<Settlement> getSettlements(){
        return this.settlements;
    }
    public void setSettlements(List<Settlement> settlements){
        this.settlements=settlements;
    }

    public void mergeSettlements(int sNum1, int sNum2){
        Settlement s1 = new Settlement(sNum1);
        Settlement s2 = new Settlement(sNum2);
        settlements.get(settlements.indexOf(s1)).addPieces(settlements.get(settlements.indexOf(s2)).getPieces());
        settlements.remove(settlements.indexOf(s2));
        s1 = s2 = null;
    }

    public boolean isOccupied(Hex location){
        Piece temp = new Piece(0,location);
        for(Settlement s: settlements){
            if(s.getPieces().contains(temp))
                return true;
        }
        return false;
    }
    public Settlement findSettlement(Hex location){
        Piece temp = new Piece(0,location);
        for(Settlement s: settlements){
            if(s.getPieces().contains(temp)){
                return s;
            }
        }
        return null;
    }
    public Settlement findSettlement(int sNum){
        return settlements.get(settlements.indexOf(sNum));
    }
    
    //When settlements are nuked, call split to handle new settlements
    public List<Settlement> splitSettlement(Settlement initial){
        List<Settlement> fragments = new ArrayList();
        for(Piece p: initial.getPieces()){
            Settlement temp = new Settlement(sNumIterator,p);
            if(p.getType()==1)
                temp.setTotoro(true);
            if(p.getType()==2)
                temp.setTiger(true);
            sNumIterator++;
            fragments.add(temp);
            for(Settlement s: fragments){
                if(!s.findPieces(HexManager.findAdjPlaced(p.getLocation())).isEmpty()){
                    initial.mergeSettlements(s,temp);
                }
            }
        }
        return fragments;
    }

    public boolean isCovered(Hex h1, Hex h2, Hex h3){
        for(Settlement s: settlements){
            int covered=0;
            if(s.findPiece(h1))
                covered++;
            if(s.findPiece(h2))
                covered++;
            if(s.findPiece(h3))
                covered++;
            //Also check for standAloneTigerName playgrounds
            if(s.getPiece(h1).getType()==2 || s.getPiece(h2).getType()==2 || s.getPiece(h3).getType()==2)
                return true;
            if(s.size()<covered)
                return true;
        }
        return false;
    }

}
