package game;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;


public class ClientManager {

    public ClientManager(){

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
            outgoingMessage = "I AM" + username + " " + password;
            out.println(outgoingMessage);
            System.out.println("Client: " + outgoingMessage);

            //WAIT FOR THE TOURNAMENT TO BEGIN pid
            incomingMessage = in.readLine();
            System.out.println("Server: " + incomingMessage);
            Matcher PlayerIDMatcher = FrequentlyUsedPatterns.PlayerIDPattern.matcher(incomingMessage);
            if (PlayerIDMatcher.matches()) {
                int playerID = Integer.parseInt(PlayerIDMatcher.group(1));
                authenticated = true;
            } else
                throw new IOException();
            System.out.println("Client: Authentication is succesful!!!");
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
                //NEW CHALLENGE cid YOU WILL PLAY rounds MATCH(ES)
                serverMessage = in.readLine();
                System.out.println("Server: " + serverMessage);
                Matcher ChallengeMatcher = FrequentlyUsedPatterns.ChallengePattern.matcher(serverMessage);
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
        /*
        try {
            //NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER pid
            serverMessage = in.readLine();
            System.out.println("Server: " + serverMessage);
            Matcher NewMatchMatcher = FrequentlyUsedPatterns.NewMatchPattern.matcher(serverMessage);
            if (NewMatchMatcher.matches()) {
                opponentID = Integer.parseInt(NewMatchMatcher.group(1));
                game1 = new Game(playerID, opponentID);
                game2 = new Game(opponentID, playerID);
                game1ID = null;
                game2ID = null;

                game1.begin();
                game2.begin();

                for (int i = 0; (!game1.isGameOver() || !game2.isGamerOver()) && i < 48; i++) {
                    MoveProtocol(in, out);
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
        */
    }


    // MoveProtocol
    public void MoveProtocol(BufferedReader in, PrintWriter out) {
        String serverMessage = "";
        String clientMessage = "";
        String placeddAndBuildMsg = "";
        String gameID, moveNumber;
        int pid;
        String tileGiven;
        /*
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
                } else if (game2ID = null) {
                    game2ID = gameID;
                }
                moveNumber = serverPromptMatcher.group(3);
                tileGiven = serverPromptMatcher.group(4);

                //place and build message from the AI
                if (gameID.equals(game1ID)) {

                } else {//play for game2}
                }

                clientMessage = "GAME " + gameID + " MOVE " + moveNumber + " ";
                //add more to the client message with +=
                out.println(clientMessage);
                System.out.println("Client: " + clientMessage);
            } else if (gameForfeitedMatcher.matches()) {
                gameID = gameForfeitedMatcher.group(1);
                pid = Integer.parseInt(gameForfeitedMatcher.group(3));
                String opponentForfeitMessage = gameForfeitedMatcher.group(4);
                if (gameID.equals(game1ID) && (!game1.isGameOver())) {
                    game1.setGameOver();
                } else {
                    game2.setGameOver();
                }
            } else if (gameLostMatcher.matches()) {
                gameID = gameLostMatcher.group(1);
                pid = Integer.parseInt(gameLostMatcher.group(3));
                String opponentLostMessage = gameLostMatcher.group(4);
                if (gameID.equals(game1ID) && (!game1.isGameover())) {
                    game1.setGameOver();
                } else {
                    game2.setGameOver();
                }
            } else if (gameMovePlayerMatcher.matches()) {
                gameID = gameMovePlayerMatcher.group(1);
                pid = Integer.parseInt(gameMovePlayerMatcher.group(3));
                String opponentMoveMessage = gameMovePlayerMatcher.group(4);
                if (pid != playerID) {
                    if (gameID.equals(game1ID)) {
                        game1.opponentPlayerMove(opponentMoveMessage);
                    } else {
                        game2.oppentPlayerMove(opponentMoveMessage);
                    }
                }
            }
            System.out.println("Client: End of Move Protocol.");
        } catch (IOException ex) {
            System.out.println("Client: The Move Protocol has failed to connect to server.");
        }*/
        System.out.println("Information sent to Server");
    }
}