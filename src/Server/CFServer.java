package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CFServer {

    /*Global variable declarations here*/
    private ServerSocket server_socket;
    private ThreadPoolExecutor thread_pool;

    /*Constructors declaration here*/
    private CFServer(){
        try {
            server_socket = new ServerSocket(12600);
            thread_pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
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

        while (true){
            System.out.println("Waiting Incoming Requests");
            try {
                System.out.println("Executor");
                thread_pool.execute(new CFSRequestHandler(server_socket.accept()));
                System.out.println(thread_pool.getQueue().size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
