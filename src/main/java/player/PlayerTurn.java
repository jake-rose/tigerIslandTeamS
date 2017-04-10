package player;

import board.*;
import pieces.*;
import java.util.*;

public class OurTurn{

    //Check for underlying hexes as well as wiping out settlements
    private boolean validTile(Board board, Hex h1, Hex h2, Hex h3, SettlementManager ourSettlements, SettlementManager theirSettlements){
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
    public boolean placeTile(Board board, Hex h1, Hex h2, Hex h3, SettlementManager ourSettlements, SettlementManager theirSettlements){
        if(validTile(board,h1,h2,h3,ourSettlements,theirSettlements)){
            board.placeTile(h1,h2,h3);
            return true;
        }
        else
            return false;
    }

    //Check for occupied spaces as well as terrain & tiger level
    private boolean validPiece(Board board, SettlementManager ourSettlements, SettlementManager theirSettlements, Piece piece){
        if(!(ourSettlements.isOccupied(piece.getLocation()))){
            if(!(theirSettlements.isOccupied(piece.getLocation()))){
                if(board.getHexManager().findHex(piece.getLocation()).getTerrain!=1){
                    return (validTotoro(
                }
            }
            else
                return false;
        }
        else
            return false;
    }
    
    private boolean validTotoro(Board board, SettlementManager ourSettlements, Piece piece){
        if(piece.getType()!=2)
            return true;
        List<Hex> adjHexes = board.getHexManager().findAdjPlaced(piece.getLocation());
        for(Hex h:adjHexes){
            if(ourSettlements.isOccupied(piece.getLocation())){
                if(ourSettlements.findSettlement(h).size() >=5 && !(ourSettlements.findSettlement(h).getTotoro()))
                    return true;
            }
        }
        return false;
    }

    private boolean validTiger(Board board, SettlementManager ourSettlements, Piece piece){
        if(piece.getType()!=3)
            return true;
        List<Hex> adjHexes = board.getHexManager().findAdjPlaced(piece.getLocation());
        for(Hex h:adjHexes){
            if(ourSettlements.isOccupied(piece.getLocation())){
                if(board.getHexManager().findHex(piece.getLocation).getLevel()>=3)
                    return true;
            }
        }
        return false;
    }

    public boolean placePiece(Board board, SettlementManager ourSettlements, SettlementManager theirSettlements,Piece piece){
        if(validPiece(board,ourSettlements,theirSettlements,piece)){
            
            ourSettlements.newSettlement(piece);
            List<Hex> adjHexes = board.getHexManager().findAdjPlaced(piece.getLocation());
            for(Hex h:adjHexes){
                if(ourSettlements.isOccupied(piece.getLocation())){
                    int s1 = ourSettlements.findSettlement(piece.getLocation).getSNum();
                    int s2 = ourSettlements.findSettlement(h).getSNum();
                    ourSettlements.mergeSettlements(s1,s2);
                    if(piece.getType()==2)
                        ourSettlements.findSettlement(h).setTotoro(true);
                    if(piece.getType()==3)
                        ourSettlements.findSettlement(h).setTiger(true);
                }
            }
        }
    }
}
