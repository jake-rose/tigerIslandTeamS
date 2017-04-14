package player;

import board.Board;
import board.Hex;
import pieces.SettlementManager;

public class PlaceTile{
    //Check for underlying hexes as well as wiping out settlements
    private static boolean validTile(Board board, Hex h1, Hex h2, Hex h3, SettlementManager ourSettlements, SettlementManager theirSettlements){
        if(board.getHexManager().validTileHexes(h1,h2,h3)){
            if(!(ourSettlements.isCovered(h1,h2,h3) || theirSettlements.isCovered(h1,h2,h3))){
                if(board.getHexManager().findAdjPlaced(h1).size()>0 || board.getHexManager().findAdjPlaced(h2).size()>0 || board.getHexManager().findAdjPlaced(h3).size()>0) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else{
                return false;
            }
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
