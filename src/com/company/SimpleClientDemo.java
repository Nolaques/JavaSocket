package com.company;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleClientDemo {
    public static void main(String[] args) {

        //server host
        final String serverHost = "localhost";

        Socket socketOfClient = null;
        BufferedWriter os = null;
        BufferedReader is = null;

        try {

            //send request to connect to server is listening
            //on PC 'localhost' port 9999
            socketOfClient = new Socket(serverHost, 9999);

            //create output stream at the client (sending data to server)
            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));

            //input stream at client(receive data from the server)
            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

        }catch (UnknownHostException e){
            System.err.println("Don't know about host "+serverHost);
            return;
        }catch (IOException e){
            System.err.println("Couldn't get I/O for the connection to "+serverHost);
        }

        try {

            //write data to output stream of the client socket
            os.write("HELLO");

            //end of line
            os.newLine();

            //flush data
            os.flush();
            os.write("I am Tom Cat");
            os.newLine();
            os.flush();
            os.write("Some data from client");
            os.newLine();
            os.flush();
            os.write("QUIT");
            os.newLine();
            os.flush();

            //read data sent from the server by reading the input of the client socket
            String responseLine;
            while ((responseLine = is.readLine()) != null){
                System.out.println("Server: "+responseLine);
                if (responseLine.indexOf("OK") != -1){
//                    if (responseLine.contains("OK")){
                    break;
                }
            }

            os.close();
            is.close();
            socketOfClient.close();
        }catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        }catch (IOException e){
            System.out.println("IOException: "+e);
            }
        }
    }

