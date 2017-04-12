package player;

import board.*;
import pieces.*;
import java.util.*;

public class PlaceTile{
    //Check for underlying hexes as well as wiping out settlements
    private boolean validTile(Board board, Hex h1, Hex h2, Hex h3, SettlementManager ourSettlements, SettlementManager theirSettlements){
        if(board.getHexManager().validTileHexes(hex1,hex2,hex3)){
            if(!(ourSettlements.isCovered(h1,h2,h3) || theirSettlements.isCovered(h1,h2,h3)){
                if(board.findAdjPlaced(h1)>0 && board.findAdjPlaced(h2)>0 && board.findAdjPlaced(h3)>0)
                    return true;
                else
                    return false;
            }
            else
                return false;
        }
        else
            return false;
    }

    //Validate then put tile in stack
    public static boolean placeTile(Board board, Hex h1, Hex h2, Hex h3, SettlementManager ourSettlements, SettlementManager theirSettlements){
        if(validTile(board,h1,h2,h3,ourSettlements,theirSettlements)){
            board.placeTile(h1,h2,h3);
            return true;
        }
        else
            return false;
    }
}
