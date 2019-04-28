/*
 * Copyright (c) 2019. This file created by Gökberk Sercan Arslan. All Rights Reserved.
 */

package Server;

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
        server.getClient_pool().execute(new CFSRequestHandler(client,server));
    }

}
