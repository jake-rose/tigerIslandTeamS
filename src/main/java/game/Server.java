package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception {

        int challenges, cid, gid, orientation, pid, rid, rounds, score;

        System.out.println("Server is started");
        ServerSocket ss = new ServerSocket(9999);

        System.out.println("Server is waiting for client request");

        //Create a socket
        Socket s = ss.accept();
        System.out.println("Client Connected");

        //writes to the client
        OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
        //Reads from the client
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(os);

        System.out.println("WELCOME TO ANOTHER EDITION OF THUNDERDOME");

        String tournnamentPW = br.readLine();
        System.out.println(tournnamentPW);

        System.out.println("Two shall enter, one shall leave");

        String username = br.readLine();
        String password = br.readLine();
        System.out.println(username);
        System.out.println(password);

        System.out.println("Wait for the tournament to begin");
        out.flush();

        //End of file
        System.out.println("Data sent from Server to Client");
    }
}