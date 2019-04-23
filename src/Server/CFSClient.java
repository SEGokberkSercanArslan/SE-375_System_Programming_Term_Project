package Server;

import java.net.Socket;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
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

    public CFSClient(Socket client, byte[] privateKey, byte[] publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.client = client;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.privateKeyClassVersion = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKey));
        this.publicKeyClassVersion = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey));
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
