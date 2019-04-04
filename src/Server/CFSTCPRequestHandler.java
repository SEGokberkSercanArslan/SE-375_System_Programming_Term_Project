package Server;

import java.io.*;
import java.net.Socket;

public class CFSTCPRequestHandler implements Runnable{

    /*Global variable declarations here*/
    private Socket client;
    private DataInputStream input;
    private DataOutputStream out;


    /*Constructors declaration here*/
    public CFSTCPRequestHandler(Socket client){
        this.client = client;
        System.out.println("Client Connected : " + this.client.getInetAddress().getHostAddress());
        try {
            input = new DataInputStream(new BufferedInputStream(client.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*Function definitions here*/
    @Override
    public void run() {
        while (!client.isClosed()) {

        }
    }
}

    /*Global variable declarations here*/

    /*Constructors declaration here*/

    /*Function definitions here*/
