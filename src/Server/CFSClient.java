package Server;

import java.net.Socket;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class CFSClient {

    private Socket client;
    private byte[] privateKey;
    private byte[] publicKey;
    private PrivateKey privateKeyClassVersion;
    private PublicKey publicKeyClassVersion;
    private String username;

    public CFSClient(Socket client, KeyPair keyPair) {
        this.client = client;
        this.privateKey = keyPair.getPrivate().getEncoded();
        this.publicKey = keyPair.getPublic().getEncoded();
        this.privateKeyClassVersion = keyPair.getPrivate();
        this.publicKeyClassVersion = keyPair.getPublic();
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


    public PrivateKey getPrivateKeyClassVersion() {
        return privateKeyClassVersion;
    }

    public PublicKey getPublicKeyClassVersion() {
        return publicKeyClassVersion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
