package game;

import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;

public class Client {

    public static void main(String[] args) throws Exception {

        int challenges, gid, orientation, pid, rid, rounds, score, moveNum;

        String cid = args[0]; //client ID

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

        System.out.println("Information sent to Server");


    }
}
