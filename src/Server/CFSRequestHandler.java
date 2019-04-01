package Server;

import java.io.*;
import java.net.Socket;

public class CFSRequestHandler implements Runnable{

    /*Global variable declarations here*/
    private Socket client;
    private DataInputStream input;
    private DataOutputStream out;


    /*Constructors declaration here*/
    public CFSRequestHandler(Socket client){
        this.client = client;
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
            System.out.println(client.getInetAddress());
            try {
                System.out.println(input.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

    /*Global variable declarations here*/

    /*Constructors declaration here*/

    /*Function definitions here*/
