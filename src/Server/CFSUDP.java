package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CFSUDP implements Runnable {

    private DatagramSocket udp_socket;
    private DatagramPacket packet;
    private ThreadPoolExecutor executor;

    public CFSUDP(DatagramSocket socket){
        this.udp_socket = socket;
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
    }

    @Override
    public void run() {
        while (true){
            try {
                udp_socket.receive(packet);
                this.executor.execute(new CFSUDPRequestHandler(udp_socket,packet));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
