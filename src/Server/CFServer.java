package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CFServer {

    /*Global variable declarations here*/
    private ServerSocket server_tcp_socket;
    private ThreadPoolExecutor client_pool;
    private ThreadPoolExecutor incoming_request_pool;
    private ArrayList<Socket> clients = new ArrayList<Socket>();
    private final int pool_size = 50;
    private final int incoming_pool_size = 5;

    /*Constructors declaration here*/
    private CFServer(){
        this.client_pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(pool_size);
        this.incoming_request_pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(incoming_pool_size);
        try {
            this.server_tcp_socket = new ServerSocket(12600);
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
            try {
                this.incoming_request_pool.execute(new CFSIncomingClientHandler(this,server_tcp_socket.accept()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean is_client_online(Socket client){
        for (int index = 0; index < clients.size(); index++) {
            if (clients.get(index).getInetAddress().equals(client.getInetAddress())){
                return true;
            }
        }
        return false;
    }

    protected void send_json_package(Socket client,String json_package) throws IOException {
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        out.writeUTF(json_package);
    }

    public ArrayList<Socket> getClients() {
        return clients;
    }

    public ThreadPoolExecutor getClient_pool() {
        return client_pool;
    }
}
