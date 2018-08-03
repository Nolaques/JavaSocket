package SocketAndThread;

import com.sun.corba.se.impl.activation.NameServiceStartThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProgram {

    public static void main(String[] args) throws IOException{

        ServerSocket listener = null;

        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;

        //try to open a server socket on port 7777
        //can't choose port less 1023 if not privileged user / root

        try {
            listener = new ServerSocket(7777);
        }catch (IOException e){
            System.out.println(e);
            System.exit(1);
        }

        try {
            while (true){
                //accept client connection request; get new socket at server

                Socket socketOfServer = listener.accept();
                new ServiceThread(socketOfServer, clientNumber++).start();
            }
        }finally {
            listener.close();
        }
    }

    private static void log(String message){
        System.out.println(message);
    }

    private static class ServiceThread extends Thread{

        private int clientNumber;
        private Socket socketOfServer;

        public ServiceThread(Socket socketOfServer, int clientNumber) {
            this.clientNumber = clientNumber;
            this.socketOfServer = socketOfServer;


        //log
        log("New connection with client ¹" + this.clientNumber + " at "+ socketOfServer);
    }

    @Override
    public void run(){

        try {
            //open input and output streams
            BufferedReader is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));

            while (true){
                //read data to server(sent from client)
                String line = is.readLine();

                //write to socket of Server
                //send to client
                os.write(">> "+line);
                //end of line
                os.newLine();
                //flush data
                os.flush();


                //if users send QUIT(to end conversation
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
    }
  }
}