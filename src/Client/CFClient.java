package Client;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class CFClient {

    /*Global variable declarations here*/
    private boolean is_active = false;
    private Socket client_socket;
    private DataOutputStream out;
    private DataInputStream input;
    private DocumentBuilderFactory document_factory;
    private DocumentBuilder document_builder;
    private Document document;

    /*Constructors declaration here*/
    private CFClient(){
        try {
            System.out.println(InetAddress.getLocalHost().getHostAddress());
            client_socket = new Socket(InetAddress.getLocalHost().getHostAddress(),12600);
            input = new DataInputStream(client_socket.getInputStream());
            out = new DataOutputStream(client_socket.getOutputStream());
            document_factory = DocumentBuilderFactory.newInstance();
            document_builder = document_factory.newDocumentBuilder();
            document = document_builder.newDocument();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /*Main programme declaration here*/
    public static void main(String[] args) {
        CFClient client = new CFClient();
        client.start();
    }

    /*Function definitions here*/

    private void start(){
        is_active = true;
        while (is_active){
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice){
                case 0:sign_in();
                    break;
                case 1:sign_up();
                    break;
                case 2: is_active = false;
                    break;
                default: System.out.println("Wrong Input");
            }
        }
        System.out.println("Thanks for playing Collect Four.");
    }

    private void sign_in(){
        try {
            out.writeUTF("Hello World");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sign_up(){
        try {
            client_socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
