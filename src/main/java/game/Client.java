package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws UnknownHostException, Exception {

        int challenges, gid, orientation, pid, rid, rounds, score, moveNum;

        String cid; //client ID

        String ip = "localhost";
        int port = 9999;

        Socket s = new Socket(ip, port);

        //Writes to server
        OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
        PrintWriter out = new PrintWriter(os);

        //Reads incoming File
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

        //Create a scanner
        Scanner scanner = new Scanner(System.in);

        //Get the Tournament password
        System.out.println("ENTER THUNDERDOME //Input toury pw ");
        String tournamentPW = scanner.next();
        out.println(tournamentPW);

        //Get the Username and Password
        System.out.println("I AM //enter username and password ");
        String username = scanner.next();
        String password = scanner.next();
        out.println(username);
        out.println(password);
        os.flush();


        // All build options will be done here
//        int buildOption;
        //      switch (buildOption){
        //        case 0: out.println("GAME "+gid +" MOVE " +moveNum+" PLACE " + /*tile+*/ " AT "+ /*xyz& orientation+*/ " FOUND SETTLEMENT AT " /*+XYZ+*/ ) ;
        //      case 1: out.println("GAME "+gid +" MOVE " +moveNum+" PLACE " + /*tile+*/ " AT "+ /*xyz& orientation+*/ " EXPAND SETTLEMENT AT " /*+XYZ<TERRIAN>+*/ ) ;
        //    case 2: out.println("GAME "+gid +" MOVE " +moveNum+" PLACE " + /*tile+*/ " AT "+ /*xyz& orientation+*/ " BUILD TOTORO SANCTUARY AT " /*+XYZ+*/ ) ;
        //  case 3: out.println("GAME "+gid +" MOVE " +moveNum+" PLACE " + /*tile+*/ " AT "+ /*xyz& orientation+*/ " BUILD TIGER PLAYGROUND AT " /*+XYZ+*/ ) ;
        //case 4: out.println("GAME "+gid +" MOVE " +moveNum+" PLACE " + /*tile+*/ " AT "+ /*xyz& orientation+*/ " UNABLE TO BUILD ") ;

        // }


        System.out.println("Information sent to Server");


    }
}