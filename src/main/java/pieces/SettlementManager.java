import src.main.java.pieces
import java.util.*;

class SettlementManager(){
    private List<Settlement> settlements;
    public void mergeSettlements(Settlement s1, Settlement s2){
        s1.addAll(s2);
        s2 = null;
    }
}
