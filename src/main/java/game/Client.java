package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    Socket s;

    public Client(String serverName, int portNumber){
        try{
            System.out.println("Connecting to Server: " + serverName + " on port number: " + portNumber + " ...");
            s = new Socket(serverName, portNumber);
            System.out.println("Connection established.");
        }catch(IOException ex){
            System.out.println("Connection not established.");
        }
    }

    public BufferedReader getDataInputStream(){
        BufferedReader dataInputStream = null;
        try{
            dataInputStream = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch(IOException ex){
            System.out.println("Failed to obtain a DataInputStream.");
        }
        return dataInputStream;
    }

    public PrintWriter getDataOutputStream(){
        PrintWriter dataOutputStream = null;
        try{
            dataOutputStream = new PrintWriter(s.getOutputStream(), true);
        } catch(IOException ex){
            System.out.println("Failed to obtain a DataOutputStream.");
        }
        return dataOutputStream;
    }

}