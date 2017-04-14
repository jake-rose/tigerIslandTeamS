package game;

/**
 * Created by TK on 2017/4/12.
 */

import org.omg.CORBA.TIMEOUT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;

import static game.FrequentlyUsedPatterns.PlayerIDPattern;
import java.lang.*;



public class CaiClient {

    private static TigerLandFinalTeamS game1;
    private static TigerLandFinalTeamS game2;

    public static void main(String[] args) throws Exception{
        ClientManager clientManager = new ClientManager();

        clientManager.TournamentProtocol();

    }

    public CaiClient(){

        int challenges, gid, orientation, pid, rid, rounds, score, moveNum;
        boolean gameEnded;

        String cid; //client ID

        //Connects to server
        String ip = "localhost";
        int port = 6969;
        Client s = new Client(ip, port);

        //Writes to server
        //OutputStreamWriter os = new OutputStreamWriter(Client.getDataOutputStream());
        PrintWriter out = Client.getDataOutputStream();

        //Reads incoming File
        //  BufferedReader br = new BufferedReader(new InputStreamReader(s.getDataInputStream()));
        //create a scanner

        Scanner scanner = new Scanner(System.in);

        String tournamentPassword = "heygang";

    }



    //Tournament protocol

    public void TournamentProtocol() {
        String serverMessage = "";

        //Reads incoming File
        BufferedReader dataInputStream = Client.getDataInputStream();
        //Writes to server
        PrintWriter dataOutputStream = Client.getDataOutputStream();

        boolean authenticated = false;
        try {
            do {
                serverMessage = dataInputStream.readLine();
                System.out.println("Server: " + serverMessage);
            } while (!FrequentlyUsedPatterns.WelcomePattern.matcher(serverMessage).matches());

            while (!authenticated) {
                authenticated = AuthenticationProtocol(dataInputStream, dataOutputStream);
            }
            System.out.println("Client: Authentication was approved we are back in Tournament Pro.");

            ChallengeProtocol(dataInputStream, dataOutputStream);

            //The Thankyou for playing Message
            serverMessage = dataInputStream.readLine();
            System.out.println("Server: " + serverMessage);


        } catch (IOException ex) {
            System.out.println("Failed to receive message from server.");
        }
    }


    //Authentication Protocol
    public boolean AuthenticationProtocol(BufferedReader in, PrintWriter out) {
        boolean authenticated = false;
        String tournamentPassword = "heygang";
        String username = "S";
        String password = "S";
        String incomingMessage, outgoingMessage;
        try {
            //ENTER THUNDERDOME *input the tournament password
            outgoingMessage = "ENTER THUNDERDOME " + tournamentPassword;
            out.println(outgoingMessage);
            System.out.println("Client: " + outgoingMessage);

            //TWO SHALL ENTER, ONE SHALL LEAVE
            incomingMessage = in.readLine();
            System.out.println("Server: " + incomingMessage);

            //I AM username password
            outgoingMessage = "I AM " + username;
            outgoingMessage+= " ";
            outgoingMessage+= password;
            out.println(outgoingMessage);
            System.out.println("Client: " + outgoingMessage);

            //WAIT FOR THE TOURNAMENT TO BEGIN pid
            incomingMessage = in.readLine();
            System.out.println("Server: " + incomingMessage);
            Matcher PlayerIDMatcher = PlayerIDPattern.matcher(incomingMessage);
            System.out.println("PlayerIDMatcher: "+PlayerIDPattern);
            if (PlayerIDMatcher.matches()) {
                int playerID = Integer.parseInt(PlayerIDMatcher.group(1));
                System.out.println("Player ID was a MATCH!!");
                authenticated = true;
            } else
                throw new IOException();
            System.out.println("Client: Authentication is successful!!!");
        } catch (IOException ex) {
            System.out.println("Client: Failed to Authenticate.");
        }
        return authenticated;
    }

    //ChallengeProtocol
    public void ChallengeProtocol(BufferedReader in, PrintWriter out) {
        String serverMessage = "";
        boolean nextGame = true;
        int challengeId, numberOfRounds = 0;
        try {
            while (nextGame) {
                System.out.println("Client: We are in the nextGame while loop.");
                //NEW CHALLENGE cid YOU WILL PLAY rounds MATCH(ES)
                serverMessage = in.readLine();
                System.out.println("ChallengeProtocol~ Server Message: "+ serverMessage);
                System.out.println("Server: " + serverMessage);
                Matcher ChallengeMatcher = FrequentlyUsedPatterns.ChallengePattern.matcher(serverMessage);
                System.out.println("ChallengeMatcher: "+ChallengeMatcher);
                if (ChallengeMatcher.matches()) {
                    challengeId = Integer.parseInt(ChallengeMatcher.group(1));
                    numberOfRounds = Integer.parseInt(ChallengeMatcher.group(2));
                }
                for (int i = 1; i <= numberOfRounds; i++) {
                    RoundProtocol(in, out);
                }
                //Wait for the next challenge to start
                serverMessage = in.readLine();
                System.out.println("Server: " + serverMessage);
                if (FrequentlyUsedPatterns.ChallengeOverPattern.matcher(serverMessage).matches()) {
                    nextGame = false;
                }
            }
            System.out.println("Client: end of Challenge Protocol.");
        } catch (IOException ex) {
            System.out.println("Failed to get challenge Protocol from server");
        }
    }
    //Round Protocol
    public void RoundProtocol(BufferedReader in, PrintWriter out) {
        String serverMessage = "";
        try {
            serverMessage = in.readLine();
            System.out.println("Server: " + serverMessage);

            while (!FrequentlyUsedPatterns.RoundBeginPattern.matcher(serverMessage).matches()) {
                serverMessage = in.readLine();
                System.out.println("Server: " + serverMessage);
            }
            MatchProtocol(in, out);

            //END OF ROUND rid OF rounds OR END OF ROUND rid OF rounds WAIT FOR THE NEXT MATCH
            serverMessage = in.readLine();
            System.out.println("Server: " + serverMessage);
            System.out.println("Client: End of Round Protocol");
        } catch (IOException ex) {
            System.out.println("Round Protocol didnt get message from server");
        }
    }

    //Match Protocol
    public void MatchProtocol(BufferedReader in, PrintWriter out) {
        String serverMessage = "";
        String clientMessage = "";

        int messageOutOut = 0;

        try {
            //NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER pid
            serverMessage = in.readLine();
            System.out.println("Server: " + serverMessage);
            Matcher NewMatchMatcher = FrequentlyUsedPatterns.NewMatchPattern.matcher(serverMessage);
            Matcher PlayerIDMatcher = FrequentlyUsedPatterns.PlayerIDPattern.matcher(serverMessage);
            Matcher GameOverMatcher = FrequentlyUsedPatterns.GameOverPattern.matcher(serverMessage);


            if (NewMatchMatcher.matches()) {
                int opponentID = Integer.parseInt(NewMatchMatcher.group(1));
                int playerID = Integer.parseInt(PlayerIDMatcher.group(1));

                int game1ID = Integer.parseInt(GameOverMatcher.group(1));
                int game2ID = Integer.parseInt(GameOverMatcher.group(1));
                this.game1 = new TigerLandFinalTeamS(playerID,opponentID);
                this.game2 = new TigerLandFinalTeamS(playerID,opponentID);



                for (int i = 0; i < 48; i++) {
                    MoveProtocol(in, out, this.game1);
                    MoveProtocol(in, out, this.game2);
                }

                //game1 score Message: GAME gid OVER PLAYER pid score PLAYER pid score
                serverMessage = in.readLine();
                System.out.println("Server: " + serverMessage);

                //game2 score Message: GAME gid OVER PLAYER pid score PLAYER pid score
                serverMessage = in.readLine();
                System.out.println("Server: " + serverMessage);
            }
            System.out.println("Client: End of Match Protocol.");
        } catch (IOException ex) {
            System.out.println("Client: Failed to connect with Match Protocol");
        }

    }


    // MoveProtocol
    public void MoveProtocol(BufferedReader in, PrintWriter out,TigerLandFinalTeamS game) {
        String serverMessage = "";
        String clientMessage = "";
        String placeddAndBuildMsg = "";
        String gameID, moveNumber;
        String game1ID=null, game2ID=null;
        int pid;
        String tileGiven;
        String t1 = "",t2 = "";

        try {
            //GAME gid MOVE # PLAYER pid move
            serverMessage = in.readLine();
            System.out.println("Server: " + serverMessage);
            Matcher serverPromptMatcher = FrequentlyUsedPatterns.MoveServerPromptPattern.matcher(serverMessage);
            Matcher gameMovePlayerMatcher = FrequentlyUsedPatterns.GameMovePlayerPattern.matcher(serverMessage);
            Matcher gameForfeitedMatcher = FrequentlyUsedPatterns.GameForfeitedPattern.matcher(serverMessage);
            Matcher gameLostMatcher = FrequentlyUsedPatterns.GameLostPattern.matcher(serverMessage);
            if (serverPromptMatcher.matches()) {
                gameID = serverPromptMatcher.group(1);
                if (game1ID == null) {
                    game1ID = gameID;
                }
                else if (game2ID == null) {
                    game2ID = gameID;
                }
                moveNumber = serverPromptMatcher.group(3);
                tileGiven = serverPromptMatcher.group(4);
                int temp=tileGiven.indexOf("+");
                t1=tileGiven.substring(0,temp);
                t2=tileGiven.substring(temp+1,tileGiven.length());
                int hex1=Integer.parseInt(t1), hex2=Integer.parseInt(t2);
                System.out.println("this is the tileGiven: "+tileGiven);



                //place and build message from the AI
                if (gameID.equals(game1ID)) {
                    clientMessage = "GAME " + gameID + " MOVE " + moveNumber + " PLACE "+tileGiven + " AT "
                            + TigerLandFinalTeamS.AIMoveX() + TigerLandFinalTeamS.AIMoveY() + TigerLandFinalTeamS.AIMoveZ() + TigerLandFinalTeamS.AIMoveO();

                   if(TigerLandFinalTeamS.AIChooseMeelple() == 1){
                            //new settlement
                            clientMessage+=" FOUND SETTLEMENT AT ";
                            clientMessage+= TigerLandFinalTeamS.AIMeepleMoveX()+" "+ TigerLandFinalTeamS.AIMeepleMoveY()+" "+TigerLandFinalTeamS.AIMeepleMoveZ();
                            }
                    if(TigerLandFinalTeamS.AIChooseTiger() == 1){
                            //place tiger
                            clientMessage+=" BUILD TIGER PLAYGROUND AT ";
                            clientMessage+= TigerLandFinalTeamS.AITigerMoveX()+" "+ TigerLandFinalTeamS.AITigerMoveY()+" "+TigerLandFinalTeamS.AITigerMoveZ();
                    }



                } else {//play for game2
                    clientMessage = "GAME " + gameID + " MOVE " + moveNumber + " PLACE "+tileGiven + " AT "+
                            TigerLandFinalTeamS.AIMoveX() + TigerLandFinalTeamS.AIMoveY() + TigerLandFinalTeamS.AIMoveZ() + TigerLandFinalTeamS.AIMoveO();

                    if(TigerLandFinalTeamS.AIChooseMeelple() == 1){
                        //new settlement
                        clientMessage+=" FOUND SETTLEMENT AT ";
                        clientMessage+= TigerLandFinalTeamS.AIMeepleMoveX()+" "+ TigerLandFinalTeamS.AIMeepleMoveY()+" "+TigerLandFinalTeamS.AIMeepleMoveZ();
                    }
                    if(TigerLandFinalTeamS.AIChooseTiger() == 1){
                        //place tiger
                        clientMessage+=" BUILD TIGER PLAYGROUND AT ";
                        clientMessage+= TigerLandFinalTeamS.AITigerMoveX()+" "+ TigerLandFinalTeamS.AITigerMoveY()+" "+TigerLandFinalTeamS.AITigerMoveZ();
                    }
                }

                out.println(clientMessage);
                System.out.println("Client: " + clientMessage);
            } else if (gameForfeitedMatcher.matches()) {
                gameID = gameForfeitedMatcher.group(1);
                pid = Integer.parseInt(gameForfeitedMatcher.group(3));
                String opponentForfeitMessage = gameForfeitedMatcher.group(4);
                if (gameID.equals(game1ID))
                {
                    this.game1=null;
                } else {
                    this.game2 = null;
                }
            } else if (gameLostMatcher.matches()) {
                gameID = gameLostMatcher.group(1);
                pid = Integer.parseInt(gameLostMatcher.group(3));
                String opponentLostMessage = gameLostMatcher.group(4);
                if (gameID.equals(game1ID)) {
                    this.game1 = null;
                } else {
                    this.game2 = null;
                }
            } else if (gameMovePlayerMatcher.matches()) {
                gameID = gameMovePlayerMatcher.group(1);
                pid = Integer.parseInt(gameMovePlayerMatcher.group(3));
                String opponentMoveMessage = gameMovePlayerMatcher.group(4);
                if (pid != game1.getPid()) {
                    Matcher PlacementMatcher = FrequentlyUsedPatterns.PlacementPattern.matcher(serverMessage);
                    Matcher BuildMatcher = FrequentlyUsedPatterns.BuildPattern.matcher(serverMessage);
                    String s = PlacementMatcher.group(1);
                    String temp = PlacementMatcher.group(5);
                    int w= 0;
                    if (gameID.equals(game1ID)) {
                        if (temp == "ROCK")
                            w = 1;
                        else if (temp == "JUNGLE")
                           w = 2;
                        else if (temp == "LAKE")
                            w = 3;
                        else if (temp == "GRASS")
                            w = 4;

                        if (s.equals("FOUND SETTLEMENT")) {//new settlement
                            TigerLandFinalTeamS.piecePlace(Integer.parseInt(BuildMatcher.group(2)), Integer.parseInt(PlacementMatcher.group(3)), Integer.parseInt(PlacementMatcher.group(4)),1,0);

                        } else if (s.equals("BUILD TOTORO SANCTUARY")) {//place totoro
                            TigerLandFinalTeamS.piecePlace(Integer.parseInt(BuildMatcher.group(2)), Integer.parseInt(PlacementMatcher.group(3)), Integer.parseInt(PlacementMatcher.group(4)),2,0);

                        } else if (s.equals("EXPAND SETTLEMENT")) {//expand settlement
                           TigerLandFinalTeamS.piecePlace(Integer.parseInt(BuildMatcher.group(2)), Integer.parseInt(PlacementMatcher.group(3)), Integer.parseInt(PlacementMatcher.group(4)), 4,w);

                        } else if (s.equals("BUILD TIGER PLAYGROUND")) {//place tiger
                            TigerLandFinalTeamS.piecePlace(Integer.parseInt(BuildMatcher.group(2)), Integer.parseInt(PlacementMatcher.group(3)), Integer.parseInt(PlacementMatcher.group(4)),3,0);

                        }
                        game1.placeTile( Integer.parseInt(PlacementMatcher.group(2)),
                                Integer.parseInt(PlacementMatcher.group(3)),
                                Integer.parseInt(PlacementMatcher.group(4)),(Integer.parseInt(PlacementMatcher.group(5))),
                               t1,t2);
                    } else {
                        if (temp == "ROCK")
                            w = 1;
                        else if (temp == "JUNGLE")
                            w = 2;
                        else if (temp == "LAKE")
                            w = 3;
                        else if (temp == "GRASS")
                            w = 4;

                        if (s.equals("FOUND SETTLEMENT")) {//new settlement
                            TigerLandFinalTeamS.piecePlace(Integer.parseInt(BuildMatcher.group(2)), Integer.parseInt(PlacementMatcher.group(3)), Integer.parseInt(PlacementMatcher.group(4)),1,0);

                        } else if (s.equals("BUILD TOTORO SANCTUARY")) {//place totoro
                            TigerLandFinalTeamS.piecePlace(Integer.parseInt(BuildMatcher.group(2)), Integer.parseInt(PlacementMatcher.group(3)), Integer.parseInt(PlacementMatcher.group(4)),2,0);

                        } else if (s.equals("EXPAND SETTLEMENT")) {//expand settlement
                            TigerLandFinalTeamS.piecePlace(Integer.parseInt(BuildMatcher.group(2)), Integer.parseInt(PlacementMatcher.group(3)), Integer.parseInt(PlacementMatcher.group(4)), 4,w);

                        } else if (s.equals("BUILD TIGER PLAYGROUND")) {//place tiger
                            TigerLandFinalTeamS.piecePlace(Integer.parseInt(BuildMatcher.group(2)), Integer.parseInt(PlacementMatcher.group(3)), Integer.parseInt(PlacementMatcher.group(4)),3,0);
                        }
                        game1.placeTile( Integer.parseInt(PlacementMatcher.group(2)),
                                Integer.parseInt(PlacementMatcher.group(3)),
                                Integer.parseInt(PlacementMatcher.group(4)),(Integer.parseInt(PlacementMatcher.group(5))),
                                t1,t2);
                    }
                }
            }
            System.out.println("Client: End of Move Protocol.");
        } catch (IOException ex) {
            System.out.println("Client: The Move Protocol has failed to connect to server.");
        }
        System.out.println("Information sent to Server");
    }
}