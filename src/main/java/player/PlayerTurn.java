package player;

import board.*;
import pieces.*;
import java.util.*;

public class OurTurn{
    private board.Board board = new board.Board();
    //Check for underlying hexes as well as wiping out settlements
    private boolean validTile(Hex h1, Hex h2, Hex h3){
        if(board.getHexManager().validTileHexes(h1,h2,h3)){
            if(!(sManager.isCovered(h1,h2,h3) || theirSettlements.isCovered(h1,h2,h3))
                return true;
            else
                return false;
        }
        else
            return false;
    }

    //Validate then put tile in stack
    public boolean placeTile(Board board, Hex h1, Hex h2, Hex h3){
        if(validTile(h1,h2,h3)){
            board.placeTile(h1,h2,h3);
            return true;
        }
        else
            return false;
    }

    //Check for occupied spaces as well as terrain & tiger level
    private boolean validPiece(Piece piece){
        if(!(sManager.isOccupied(piece.getLocation()))){
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

    private boolean validTotoro(Board board, SettlementManager sManager, Piece piece){
        if(piece.getType()!=2)
            return true;
        List<Hex> adjHexes = board.getHexManager().findAdjPlaced(piece.getLocation());
        for(Hex h:adjHexes){
            if(sManager.isOccupied(piece.getLocation())){
                if(sManager.findSettlement(h).size() >=5)
                    return true;
            }
        }
        return false;
    }

    public boolean placePiece(Board board, SettlementManager sManager, Piece piece){
        if(validPiece(piece)){

            sManager.newSettlement(piece);
            List<Hex> adjHexes = board.getHexManager().findAdjPlaced(piece.getLocation());
            for(Hex h:adjHexes){
                if(sManager.isOccupied(piece.getLocation())){
                    int s1 = sManager.findSettlement(piece.getLocation()).getSNum();
                    int s2 = sManager.findSettlement(h).getSNum();
                    sManager.mergeSettlements(s1,s2);
                    if(piece.getType()==2)
                        sManager.findSettlement(h).setTotoro(true);
                    if(piece.getType()==3)
                        sManager.findSettlement(h).setTiger(true);
                }
            }
        }
    }
}
