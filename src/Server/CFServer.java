package Server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class CFServer {

    /*Global variable declarations here*/
    private ServerSocket tcp_socket;
    private DatagramSocket udp_socket;

    /*Constructors declaration here*/
    private CFServer(){
        try {
            tcp_socket = new ServerSocket(12600);
            udp_socket = new DatagramSocket(12601);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*Main programme declaration here*/
    public static void main(String[] args) {
        CFServer server = new CFServer();
        server.start();
    }

    /*Function definitions here*/
    private void start(){

        Thread tcp_thread = new Thread(new CFSTCP(this.tcp_socket));
        Thread udp_thread = new Thread(new CFSUDP(this.udp_socket));
        tcp_thread.start();
        udp_thread.start();

        }

    }
