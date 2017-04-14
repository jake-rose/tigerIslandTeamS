package game;

import board.*;
import pieces.*;
import player.*;
import java.util.*;

public class AI{
    private Hex prevHex;
    private int tileNum;
    private boolean newSettlement;

    public AI(){
        this.prevHex = null;
        this.tileNum = 0;
        this.newSettlement = true;
    }

    public void setPrevHex(Hex prevHex){
        this.prevHex = prevHex;
    }
    public Hex getPrevHex(){
        return this.prevHex;
    }
    public void setTileNum(int tileNum){
        this.tileNum = tileNum;
    }
    public int getTileNum(){
        return this.tileNum;
    }
    public void setNewSettlement(boolean newSettlement){
        this.newSettlement = newSettlement;
    }
    public boolean getNewSettlement(){
        return this.newSettlement;
    }
    
    public Hex tilePlacement(Board board, int t1, int t2, int t3, SettlementManager ours, SettlementManager theirs){
        List<Hex> potentialHex = board.getHexManager().getHexStack();
        int x, y, z;
        this.tileNum+=2;
        //DEBUG
        System.out.println("Placing tile...");
        if(prevHex!=null){
            x = prevHex.getCoord()[0]-2;
            y = prevHex.getCoord()[1]+1;
            z = prevHex.getCoord()[2]+1;
            Hex h1 = new Hex(x,y,z,tileNum,t1);
            x = prevHex.getCoord()[0]-1;
            y = prevHex.getCoord()[1]+1;
            z = prevHex.getCoord()[2];
            Hex h2 = new Hex(x,y,z,tileNum,t2);
            x = prevHex.getCoord()[0]-2;
            y = prevHex.getCoord()[1]+2;
            z = prevHex.getCoord()[2];
            Hex h3 = new Hex(x,y,z,tileNum,t3);
            if(PlaceTile.placeTile(board,h1,h2,h3,ours,theirs)){
                this.prevHex = h2;
                //DEBUG
                System.out.println("Tile placed");
                return h1;
            }
        }
        for(Hex h:potentialHex){
            x = h.getCoord()[0]-2;
            y = h.getCoord()[1]+1;
            z = h.getCoord()[2]+1;
            Hex h1 = new Hex(x,y,z,tileNum,t1);
            x = h.getCoord()[0]-1;
            y = h.getCoord()[1]+1;
            z = h.getCoord()[2];
            Hex h2 = new Hex(x,y,z,tileNum,t2);
            x = h.getCoord()[0]-2;
            y = h.getCoord()[1]+2;
            z = h.getCoord()[2];
            Hex h3 = new Hex(x,y,z,tileNum,t3);
            if(PlaceTile.placeTile(board,h1,h2,h3,ours,theirs)){
                this.prevHex = h2;
                //DEBUG
                System.out.println("Tile placed");
                return h1;
            }
        }
        return null;
    }

    public int[] piecePlacement(Board board, SettlementManager ours, SettlementManager theirs){
        //DEBUG
        System.out.println("Placing piece...");
        if(this.newSettlement){
            this.newSettlement = false;
            Piece temp = new Piece(0,this.prevHex);
            if(PlacePieces.placeSettlement(board,ours,theirs,temp)){
                int x = prevHex.getCoord()[0];
                int y = prevHex.getCoord()[1];
                int z = prevHex.getCoord()[2];
                int[] returnThis = {0,x,y,z};
                //DEBUG
                System.out.println("New settlement placed");
                return returnThis;
            }
            else{
                for(Hex h:board.getHexManager().getHexStack()){
                    temp = new Piece(0,h);
                    if(PlacePieces.placeSettlement(board,ours,theirs,temp)){
                        int x = h.getCoord()[0];
                        int y = h.getCoord()[1];
                        int z = h.getCoord()[2];
                        int[] returnThis = {0,x,y,z};
                        //DEBUG
                        System.out.println("New settlement placed");
                        return returnThis;
                    }
                }
            }
        }
        if(ours.getTotoros()>0){
            if(ours.getSettlements().size()>0){
                for(Settlement s:ours.getSettlements()){
                    if(s.getPieces().size()>=5){
                        List<Hex> adjHexes = new ArrayList();
                        for(Piece p:s.getPieces()){
                            adjHexes.addAll(board.getHexManager().findAdjPlaced(p.getLocation()));
                            for(Hex h: adjHexes){
                                Piece temp = new Piece(1,h);
                                if(PlacePieces.placeTotoro(board, ours, theirs, temp, s.getSNum())){
                                    int x = h.getCoord()[0];
                                    int y = h.getCoord()[1];
                                    int z = h.getCoord()[2];
                                    int[] returnThis = {1,x,y,z};
                                    //DEBUG
                                    System.out.println("Totoro placed");
                                    this.newSettlement = true;
                                    return returnThis;
                                }
                            }
                        }
                    }
                }
            }
        }
        for(Settlement s: ours.getSettlements()){
            for(int terrain=2; terrain<6; terrain++){
                if(PlacePieces.expandSettlement(board,ours,theirs,terrain,s.getSNum())){
                    //Continue expansion until no more of the same terrain adjacent
                    while(PlacePieces.expandSettlement(board,ours,theirs,terrain,s.getSNum())){}
                    //DEBUG
                    System.out.println("Expanding settlement "+s.getSNum());

                    Hex tempHex = s.getPieces().get(0).getLocation();
                    int x = tempHex.getCoord()[0];
                    int y = tempHex.getCoord()[1];
                    int z = tempHex.getCoord()[2];
                    int[] returnThis = {2,x,y,z,terrain};
                    return returnThis;
                }
            }
        }
        return null;
    }

}
