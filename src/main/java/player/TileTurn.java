package player;

import board.*;
import pieces.*;
import java.util.*;

public class TileTurn{
    private Board board;
    private HexManager hexManager;
    private SettlementManager ourSettlements;
    private SettlementManager theirSettlements;

    public TileTurn(){
        board = new Board();
        List<Hex> hexStack = new ArrayList<Hex>();
        hexManager = new HexManager(hexStack);
        ourSettlements = new SettlementManager();
        theirSettlements = new SettlementManager();
    }

    private boolean validPlacement(Hex h1, Hex h2, Hex h3){
        if(hexManager.validTileHexes(h1,h2,h3)){
            if(!(ourSettlements.isCovered(h1,h2,h3) || theirSettlements.isCovered(h1,h2,h3)))
                return true;
            else
                return false;
        }
        else
            return false;
    }
    public boolean placeTile(Hex h1, Hex h2, Hex h3){
        if(validPlacement(h1,h2,h3)){
            board.placeTile(h1,h2,h3);
            return true;
        }
        else
            return false;
    }
}
