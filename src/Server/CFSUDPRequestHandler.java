package Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class CFSUDPRequestHandler implements Runnable {

    private DatagramSocket client_udp_socket;
    private DatagramPacket client_package;

    public CFSUDPRequestHandler(DatagramSocket socket, DatagramPacket packet){
        this.client_udp_socket = socket;
        this.client_package = packet;
    }

    @Override
    public void run() {

    }
}
