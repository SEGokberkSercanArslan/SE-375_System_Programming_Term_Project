package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CFSTCP implements Runnable {

    private ServerSocket serverSocket;
    private ThreadPoolExecutor executor;

    public CFSTCP(ServerSocket server){
        this.serverSocket = server;
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
    }

    @Override
    public void run() {
        while (true){
            try {
                System.out.println("Waiting incoming TCP Connection");
                executor.execute(new CFSTCPRequestHandler(serverSocket.accept()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
