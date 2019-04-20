package Server;

import java.net.Socket;

public class CFSClient {

    private Socket client;
    private byte[] privateKey;
    private byte[] publicKey;

    public CFSClient(Socket client, byte[] privateKey, byte[] publicKey){
        this.client = client;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }


    public Socket getClient() {
        return client;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }
}
