package Playground;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TestGround {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost().getHostAddress());
        System.out.println(network.getHardwareAddress());
    }
}
