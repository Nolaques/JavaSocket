package SocketAndThread;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class ClientDemo {
    public static void main(String[] args) {

        final String serverHost = "localhost";

        Socket socketOfClient = null;
        BufferedWriter os = null;
        BufferedReader is = null;

        try {

            // send request to connect to the server is listening on PC 'localhost' port 7777

            socketOfClient = new Socket(serverHost, 7777);

            //create output stream at the client (sending data to the server)
            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));

            //input stream at Client(receive data from server)
            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));


        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverHost);
            return;
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverHost);
            return;
        }

        try {


            //write data to the output stream of the client socket
            os.write("Hello! nos is " + new Date());

            //end of line
            os.newLine();
            //flush data
            os.flush();
            os.write("I am Tom Cat");
            os.newLine();
            os.flush();
            os.write("some data");
            os.newLine();
            os.flush();
            os.write("QUIT");
            os.newLine();
            os.flush();

            //read data sent from server by reading the input stream of the client socket
            String responseLine;
            while ((responseLine = is.readLine()) != null) {
                System.out.println("Server: " + responseLine);
                if (responseLine.indexOf("OK") != -1) {
                    break;
                }
            }

            os.close();
            is.close();
            socketOfClient.close();
        } catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException: " + e);
        }
    }
}
