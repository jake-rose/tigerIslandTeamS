package TigerLand;

/**
 * Created by TK on 2017/3/22.
 */
public class TilesPhaseManager {
    int level = 0;

    public int getLevel(){
        return level;
    }

    public TilesPhaseManager(){

    }

   private TilesPhaseManager HexOne;
   private TilesPhaseManager HexTwo;
   private TilesPhaseManager volcanoHex;

   public TilesPhaseManager getHexOne(){
       return HexOne;
   }
   public TilesPhaseManager getHexTwo(){
       return HexTwo;
   }
   public TilesPhaseManager getHexThree(){
       return volcanoHex;
   }
    public boolean isLeagalPlace(){
        //check if the palcement is leagal or not
        return false;
    }
    public void setHexOne(TilesPhaseManager hex){
        HexOne = hex;
    }
    public void setHexTwo(TilesPhaseManager hex){
        HexTwo = hex;
    }
    public void setHexThree(TilesPhaseManager hex) {
        volcanoHex = hex;
    }
}

