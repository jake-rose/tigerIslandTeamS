package tigerIslandTeamS;

/**
 * Created by TK on 2017/4/2.
 */
public class Player {
    private int scores;
    private int villagersLeft;
    private int totorosLeft;
    private int tigersLeft;

//    public Player(){
//        scores = 0;
//        villagersLeft = 20;
//        totorosLeft = 3;
//        tigersLeft = 2;
//    }

    public int getScores() {
        return scores;
    }
    public int getVillagersLeft(){
        return villagersLeft;
    }
    public int getTotorosLeft(){
        return totorosLeft;
    }
    public int getTigersLeft(){
        return tigersLeft;
    }


    public void placeVillager(int numPlacedVillager){
        if(numPlacedVillager > villagersLeft){
            throw new IndexOutOfBoundsException("You don't have enough villager");
        }
        else{
            scores = numPlacedVillager;
            villagersLeft -= numPlacedVillager;
        }
    }
    public void placeTotoro(int numPlacedTotoro){
        if(numPlacedTotoro > 1){
            throw new IndexOutOfBoundsException("You can't place more than 1 totoro at one time.");
        }
        else{
            scores += 200;
            totorosLeft -= 1;
        }
    }
    public void placeTiger(int numPlacedTier){
        if(numPlacedTier > 1){
            throw new IndexOutOfBoundsException("You can't place more than 1 tiger at one time.");
        }
        else{
            scores += 75;
            tigersLeft -= 1;
        }
    }

}
