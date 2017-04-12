package game;

import java.util.*;
//import player.*;
import board.*;
import pieces.*;

public class Game{
    private int gid;
    private Board board;
    private SettlementManager ourPieces;
    private SettlementManager theirPieces;

    public void setGid(int gid){
        this.gid=gid;
    }
    public int getGid(){
        return this.gid;
    }

    public Game(int gid){

    }
}
