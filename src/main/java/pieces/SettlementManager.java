package pieces;

import board.*;
import pieces.*;
import java.util.*;

public class SettlementManager{
    private List<Settlement> settlements;
    private int sNumIterator=0;

    public void newSettlement(Piece piece){
        settlements.add(new Settlement(sNumIterator,piece));
        sNumIterator++;
    }

    public void expandSettlement(Piece piece, int sNum){
        settlements.get(settlements.indexOf(new Settlement(sNum))).addPiece(piece);
    }

    public List<Settlement> getSettlements(){
        return this.settlements;
    }
    public void setSettlements(List<Settlement> settlements){
        this.settlements=settlements;
    }

    public void mergeSettlements(int sNum1, int sNum2){
        Settlement s1 = new Settlement(sNum1);
        Settlement s2 = new Settlement(sNum1);
        settlements.get(settlements.indexOf(s1)).addPieces(settlements.get(settlements.indexOf(s2)).getPieces());
        settlements.remove(settlements.indexOf(s2));
        s1 = s2 = null;
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
            if(s.size()<covered)
                return true;
        }
        return false;
    }

}
