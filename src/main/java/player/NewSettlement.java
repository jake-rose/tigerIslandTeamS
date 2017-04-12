package player;

import board.*;
import pieces.*;
import java.util.*;

public class PlacePieces{

    public static boolean placeSettlement(Board board, SettlementManager ours, SettlementManager theirs, Piece piece){
        if(!(ours.isOccupied(piece.getLocation()))){
            if(!(theirs.isOccupied(piece.getLocation()))){
                if(board.getHexManager().findHex(piece.getLocation()).getTerrain()!=1){
                    int meeples = board.getHexManager().findHex(piece.getLocation()).getLevel();
                    ours.newSettlement(piece, meeples);
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

    public static boolean placeTotoro(Board board, SettlementManager ours, SettlementManager theirs, Piece piece, int sNum){
        if(!(ours.isOccupied(piece.getLocation()))){
            if(!(theirs.isOccupied(piece.getLocation()))){
                if(board.getHexManager().findHex(piece.getLocation()).getTerrain()!=1){
                    ours.addTotoroToSettlement(piece, sNum);
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

    public static boolean placeTiger(Board board, SettlementManager ours, SettlementManager theirs, Piece piece, int sNum){
        if(!(theirs.isOccupied(piece.getLocation()))){
            if(board.getHexManager().findHex(piece.getLocation()).getTerrain()!=1 && board.getHexManager().findHex(piece.getLocation()).getLevel()>=3){
                ours.addTigerToSettlement(piece, sNum);
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    public static boolean expandSettlement(Board board, SettlementManager ours, SettlementManager theirs, int terrain, int sNum){
        Settlement temp = ours.findSettlement(sNum);
        List<Hex> adjTerrain = new ArrayList();
        for(Piece p:temp.getPieces()){
            adjTerrain.addAll(board.adjTerrain(p,terrain));
        }
        List<Hex> expandToThese = new ArrayList();
        for(Hex h:adjTerrain){
            if(!(theirs.isOccupied(h) || ours.isOccupied(h) || expandToThese.contains(h)))
                expandToThese.add(adjTerrain.get(adjTerrain.indexOf(h)));
        }
        int meeples = 0;
        for(Hex h:expandToThese){
            meeples += h.getLevel();
        }
        if(meeples<=ours.getMeeples()){
            for(Hex h:expandToThese){
                Piece p = new Piece(0,h);
                ours.expandSettlement(p,sNum);
            }
            return true;
        }
        else
            return false;


}
