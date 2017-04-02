package TigerLand;

import java.util.ArrayList;

/**
 * Created by TK on 2017/4/2.
 */
public class Pieces {
    public enum pieces{
        Villager, Totoro, Tiger;
    }
    ArrayList<pieces> villagers = new ArrayList<pieces>(20);
    ArrayList<pieces> totoros = new ArrayList<pieces>(3);
    ArrayList<pieces> tigers = new ArrayList<pieces>(2);

    public Pieces(){
        initialVillagers();
        initialTigers();
        initialTotoros();
    }
    private void initialVillagers(){
        for(int i = 1; i <= 20; i++){
            villagers.add(pieces.Villager);
        }
    }
    private void initialTotoros(){
        for(int i = 1; i <= 3; i++){
            totoros.add(pieces.Totoro);
        }
    }
    private void initialTigers(){
        for(int i = 1; i <= 2; i++){
            tigers.add(pieces.Tiger);
        }
    }
    public void removeVillager() {
        villagers.remove(pieces.Villager);
    }
    public void removeTotoro() {
        totoros.remove(pieces.Totoro);
    }
    public void removeTiger() {
        tigers.remove(pieces.Tiger);
    }
//    public int getVillagerSize(){
//        return villagers.size();
//    }
//    public int getTotoroSize(){
//        return totoros.size();
//    }
//    public int getTigerSize(){
//        return tigers.size();
//    }
//
//    public boolean isEmpty(){
//        if(){
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
}
