package Server;

import java.io.IOException;
import java.net.Socket;

public class CFSIncomingClientHandler implements Runnable {

    private CFServer server;
    private Socket client;
    private CFSPackageFactory factory;

    public CFSIncomingClientHandler(CFServer server, Socket client){
        this.server = server;
        this.client = client;
        this.factory = new CFSPackageFactory();
    }

    @Override
    public void run() {
        if (server.is_client_online(client)){
            try {
                server.send_json_package(client,factory.error_already_online());
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            server.getClient_pool().execute(new CFSRequestHandler(client,server));
        }
    }

}
