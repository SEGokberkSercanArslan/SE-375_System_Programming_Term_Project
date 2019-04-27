package Server;

import Game.CFGameSeason;

import java.io.DataOutputStream;
import java.io.EOFException;
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
    private ArrayList<CFSClient> clients = new ArrayList<CFSClient>();
    private ArrayList<CFGameSeason> seasons = new ArrayList<CFGameSeason>();
    private final int pool_size = 50;
    private final int incoming_pool_size = 5;

    /*Constructors declaration here*/
    private CFServer(){
        this.client_pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(pool_size);
        this.incoming_request_pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(incoming_pool_size);
        try {
            this.server_tcp_socket = new ServerSocket(12600);
        } catch (EOFException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    /*Main programme declaration here*/
    public static void main(String[] args) {
        CFServer server = new CFServer();
        server.start();
    }

    /*Server starter function*/
    private void start(){
        while (true){
            try {
                this.incoming_request_pool.execute(new CFSIncomingClientHandler(this,server_tcp_socket.accept()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*Function definitions here*/

    protected boolean is_client_online(Socket client){
        for (int index = 0; index < clients.size(); index++) {
            if (clients.get(index).getClient().getInetAddress().equals(client.getInetAddress())){
                return true;
            }
        }
        return false;
    }


    protected void send_json_package(Socket client,String json_package) throws IOException {
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        out.writeUTF(json_package);
    }


    protected void add_active_client(CFSClient client){
        clients.add(client);
    }

    public ThreadPoolExecutor getClient_pool() {
        return client_pool;
    }

    public final boolean is_username_online(String username){
        for (CFSClient client : clients) {
            if (client.getUsername().equals(username) && client.is_sign_in()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<CFGameSeason> get_Seasons() {
        return seasons;
    }
}
