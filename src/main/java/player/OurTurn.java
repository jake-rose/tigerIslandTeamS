package player;

import board.*;
import pieces.*;
import java.util.*;

public class OurTurn{
    private Board board;
    private SettlementManager ourSettlements;
    private SettlementManager theirSettlements;

    public OurTurn(){
        board = new Board();
        ourSettlements = new SettlementManager();
        theirSettlements = new SettlementManager();
    }
    
    //Check for underlying hexes as well as wiping out settlements
    private boolean validTile(Hex h1, Hex h2, Hex h3){
        if(board.getHexManager().validTileHexes(hex1,hex2,hex3)){
            if(!(ourSettlements.isCovered(h1,h2,h3) || theirSettlements.isCovered(h1,h2,h3))
                return true;
            else
                return false;
        }
        else
            return false;
    }

    //Validate then put tile in stack
    public boolean placeTile(Hex h1, Hex h2, Hex h3){
        if(validTile(h1,h2,h3)){
            board.placeTile(h1,h2,h3);
            return true;
        }
        else
            return false;
    }

    //Check for occupied spaces as well as terrain & tiger level
    private boolean validPiece(Piece piece){
        if(!(ourSettlements.isOccupied(piece.getLocation()))){
            if(!(theirSettlements.isOccupied(piece.getLocation()))){
                if(board.getHexManager().findHex(piece.getLocation()).getTerrain!=1){
                    if(piece.getType()==3){
                        if(board.getHexManager().findHex(piece.getLocation()).getLevel>=3){
                            if(validTotoro(piece))
                                return true;
                            else
                                return false;
                        }
                    }
                    else
                        return true;
                }
                else
                    return false;
            }
            else
                return false;
        }
        else
            return false;
    }
    
    private boolean validTotoro(Piece piece){
        if(piece.getType()!=2)
            return true;
        List<Hex> adjHexes = board.getHexManager().findAdjPlaced(piece.getLocation());
        for(Hex h:adjHexes){
            if(ourSettlements.isOccupied(piece.getLocation())){
                if(ourSettlements.findSettlement(h).size() >=5)
                    return true;
            }
        }
        return false;
    }

    public boolean placePiece(Piece piece){
        if(validPiece(piece)){
            
            settlementManager.newSettlement(piece);
            List<Hex> adjHexes = board.getHexManager().findAdjPlaced(piece.getLocation());
            for(Hex h:adjHexes){
                if(ourSettlements.isOccupied(piece.getLocation())){
                    int s1 = ourSettlements.findSettlement(piece.getLocation).getSNum();
                    int s2 = ourSettlements.findSettlement(h).getSNum();
                    settlementManager.mergeSettlements(s1,s2);
                    if(piece.getType()==2)
                        settlementManager.findSettlement(h).setTotoro(true);
                    if(piece.getType()==3)
                        settlementManager.findSettlement(h).setTiger(true);
                }
            }
        }
    }
}
