package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServerProgram {

    public static void main(String[] args) {

        ServerSocket listener = null;
        String line;
        BufferedReader is;
        BufferedWriter os;
        Socket socketOfServer = null;

        //try to open server socket on port 9999
        //can't choosing port less then 1023 if not admin or privileged users(root)

        try {
            listener = new ServerSocket(9999);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        try {
            System.out.println("Server is waiting to accept user...");

            //accept client connection request
            //get new socket at server
            socketOfServer = listener.accept();
            System.out.println("Accept a client!");

            //open input and output streams
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));

            while (true) {
                //read data to server(sent form client)
                line = is.readLine();

                //write to socket of server
                //send to client
                os.write(">> " + line);
                //end of line
                os.newLine();
                //flush data
                os.flush();

                //if users send QUIT (to end conversation)
                if (line.equals("QUIT")){
                    os.write(">> OK");
                    os.newLine();
                    os.flush();
                    break;
                }
            }
        }catch (IOException e){
            System.out.println(e);
            e.printStackTrace();
        }
        System.out.println("Server stopped");
    }
}
