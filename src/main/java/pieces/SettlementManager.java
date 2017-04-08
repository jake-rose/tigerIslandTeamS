import src.main.java.pieces
import java.util.*;

class SettlementManager(){
    private List<Settlement> settlements;
    private int sNumIterator=0;

    public void newSettlement(Piece piece){
        settlements.add(new Settlement(sNumIterator,piece));
        sNumIterator++;
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
        settlements.get(settlements.indexOf(s1)).addAll(settlements.get(settlements.indexOf(s2)));
        settlements.remove(settlements.indexOf(s2));
        s1 = s2 = null;
    }
}
