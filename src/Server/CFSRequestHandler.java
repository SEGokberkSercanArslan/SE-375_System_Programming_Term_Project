package Server;

import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public class CFSRequestHandler implements Runnable{

    /*Global Variables*/
    private Socket client;
    private CFServer server;
    private DataInputStream stream;
    private CFSClient cfsClient;
    private CFSPackageFactory factory = new CFSPackageFactory();

    /*Constructor Decelerations*/
    public CFSRequestHandler(Socket client, CFServer server){
        this.client = client;
        this.server = server;
        try {
            this.stream = new DataInputStream(client.getInputStream());
            this.cfsClient = new CFSClient(client,publicKey(keyGenerator()),privateKey(keyGenerator()));
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!this.client.isClosed()){
            try {
                String request = stream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*Function Decelerations*/
    public void decode_request(String request) throws NoSuchAlgorithmException, IOException {

        JSONObject object = new JSONObject(request);
        String type = object.get("Type").toString();

        if (type.equals("Request")){
            if (object.get("RSA").toString().equals("RSA-PUB")){
                server.add_active_client(this.cfsClient);
                server.send_json_package(client,factory.rsa_public_key_package(cfsClient.getPublicKey()));
            }else if (object.get("RSA").toString().equals("Phase-2")){

            }
        }

    }

    /*RSA KEY GENERATORS*/
    public KeyPairGenerator keyGenerator() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        return keyPairGenerator;
    }

    public byte [] publicKey(KeyPairGenerator keyPairGenerator){
        return keyPairGenerator.genKeyPair().getPublic().getEncoded();
    }

    public byte [] privateKey(KeyPairGenerator keyPairGenerator){
        return keyPairGenerator.genKeyPair().getPrivate().getEncoded();
    }

    //Decipher Operations
    public static byte[] decrypt(PrivateKey privateKey, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encrypted);
    }

}
