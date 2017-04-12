package game;

import java.util.*;
import player.*;
import board.*;
import pieces.*;

public class Game{
    private int gid;
    private int theirTileNum;
    private Board board;
    private SettlementManager ourPieces;
    private SettlementManager theirPieces;
    private AI ai;

    public void setGid(int gid){
        this.gid=gid;
    }
    public int getGid(){
        return this.gid;
    }

    public Game(int gid){
       board = new Board();
       ourPieces = new SettlementManager();
       theirPieces = new SettlementManager();
       this.gid = gid;
       this.theirTileNum = 1;
       ai = new AI();
    }

    public void theirTile(int orientation, int a, int b, int x, int y, int z){
        Hex h1, h2, h3;
        switch(orientation){
            case 1:
                h1 = new Hex(x,y,z,theirTileNum,1);
                h2 = new Hex(x,y+1,z-1,theirTileNum,a);
                h3 = new Hex(x+1,y,z-1,theirTileNum,b);
                PlaceTile.placeTile(this.board,h1,h2,h3,this.theirPieces,this.ourPieces);
                this.theirTileNum+=2;
                break;
            case 2:
                h1 = new Hex(x,y,z,theirTileNum,1);
                h2 = new Hex(x+1,y,z-1,theirTileNum,a);
                h3 = new Hex(x+1,y-1,z,theirTileNum,b);
                PlaceTile.placeTile(this.board,h1,h2,h3,this.theirPieces,this.ourPieces);
                this.theirTileNum+=2;
                break;
            case 3:
                h1 = new Hex(x,y,z,theirTileNum,1);
                h2 = new Hex(x+1,y-1,z,theirTileNum,a);
                h3 = new Hex(x,y-1,z+1,theirTileNum,b);
                PlaceTile.placeTile(this.board,h1,h2,h3,this.theirPieces,this.ourPieces);
                this.theirTileNum+=2;
                break;
            case 4:
                h1 = new Hex(x,y,z,theirTileNum,1);
                h2 = new Hex(x,y-1,z+1,theirTileNum,a);
                h3 = new Hex(x-1,y,z+1,theirTileNum,b);
                PlaceTile.placeTile(this.board,h1,h2,h3,this.theirPieces,this.ourPieces);
                this.theirTileNum+=2;
                break;
            case 5:
                h1 = new Hex(x,y,z,theirTileNum,1);
                h2 = new Hex(x-1,y,z+1,theirTileNum,a);
                h3 = new Hex(x-1,y+1,z,theirTileNum,b);
                PlaceTile.placeTile(this.board,h1,h2,h3,this.theirPieces,this.ourPieces);
                this.theirTileNum+=2;
                break;
            case 6:
                h1 = new Hex(x,y,z,theirTileNum,1);
                h2 = new Hex(x-1,y+1,z,theirTileNum,a);
                h3 = new Hex(x,y+1,z-1,theirTileNum,b);
                PlaceTile.placeTile(this.board,h1,h2,h3,this.theirPieces,this.ourPieces);
                this.theirTileNum+=2;
                break;
        }
    }

    public void theirNewSettlement(int x, int y, int z){
        Piece temp = new Piece(0,x,y,z);
        PlacePieces.placeSettlement(this.board,this.theirPieces,this.ourPieces,temp);
    }
    public void theirExpandSettlement(int x, int y, int z, int terrain){
        Piece temp = new Piece(0,x,y,z);
        for(Settlement s:this.theirPieces.getSettlements()){
            if(s.getPieces().contains(temp)){
                while(PlacePieces.expandSettlement(board, theirPieces, ourPieces, terrain, s.getSNum())){}
                break;
            }
        }
    }

    public void theirTotoro(int x, int y, int z){
        Piece temp = new Piece(1,x,y,z);
        Hex location = new Hex(x,y,z);
        List<Hex> adjHexes = board.getHexManager().findAdjPlaced(location);
        for(Hex h:adjHexes){
            if(theirPieces.findSettlement(h)!=null){
                location=h;
                break;
            }
        }
        int sNum = theirPieces.findSettlement(location).getSNum();
        PlacePieces.placeTotoro(this.board, this.theirPieces, this.ourPieces, temp, sNum); 
    }
    
    public void theirTiger(int x, int y, int z){
        Piece temp = new Piece(2,x,y,z);
        Hex location = new Hex(x,y,z);
        List<Hex> adjHexes = board.getHexManager().findAdjPlaced(location);
        for(Hex h:adjHexes){
            if(theirPieces.findSettlement(h)!=null){
                location=h;
                break;
            }
        }
        int sNum = theirPieces.findSettlement(location).getSNum();
        PlacePieces.placeTiger(this.board, this.theirPieces, this.ourPieces, temp, sNum); 
    }

    public int[] ourTile(int a, int b){
        Hex h = ai.tilePlacement(this.board,1,a,b,ourPieces,theirPieces);
        int[] tile = {1,h.getCoord()[0],h.getCoord()[1],h.getCoord()[2]};
        return tile;
    }

    public int[] ourPiece(){
        return ai.piecePlacement(this.board, this.ourPieces, this.theirPieces);
    }
}
