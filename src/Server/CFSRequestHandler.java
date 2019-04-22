package Server;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.Cipher;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

public class CFSRequestHandler implements Runnable{

    /*Global Variables*/
    private Socket client;
    private CFServer server;
    private DataInputStream stream_input;
    private DataOutputStream stream_out;
    private CFSClient cfsClient;
    private CFSPackageFactory factory = new CFSPackageFactory();

    /*Constructor Decelerations*/
    public CFSRequestHandler(Socket client, CFServer server){
        this.client = client;
        this.server = server;
        try {
            this.stream_input = new DataInputStream(client.getInputStream());
            this.stream_out = new DataOutputStream(client.getOutputStream());
            this.cfsClient = new CFSClient(client,publicKey(keyGenerator()),privateKey(keyGenerator()));
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!this.client.isClosed()){
            try {
                String request = stream_input.readUTF();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*Function Decelerations*/
    public void decode_request(String request) throws Exception {

        JSONObject object = new JSONObject(request);

        if (object.get("Type").toString().equals("Request")){
            if (object.get("Request").toString().equals("RSA-PUB")){
                server.add_active_client(this.cfsClient);
                server.send_json_package(client,factory.rsa_public_key_package(cfsClient.getPublicKey()));
            }else if (object.get("Request").toString().equals("Sign-Up")){
                String username = object.get("Username").toString();
            }else if (object.get("Request").toString().equals("Sign-In")){

            }else if (object.get("Request").toString().equals("Forget-Password-Phase-1")){
                String username = object.get("Username").toString();
            }else if (object.get("Request").toString().equals("Forget-Password-Phase-2")){
                String username = object.get("Username").toString();
                String answer = byte_to_string(decrypt(this.cfsClient.getPrivateKeyClassVersion(),json_array_to_byte_array((JSONArray) object.get("Answer"))));
            }
        }

    }

    /*RSA KEY GENERATORS*/
    private KeyPairGenerator keyGenerator() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        return keyPairGenerator;
    }

    private byte [] publicKey(KeyPairGenerator keyPairGenerator){
        return keyPairGenerator.genKeyPair().getPublic().getEncoded();
    }

    private byte [] privateKey(KeyPairGenerator keyPairGenerator){
        return keyPairGenerator.genKeyPair().getPrivate().getEncoded();
    }

    //Decipher Operations
    public byte[] decrypt(PrivateKey privateKey, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encrypted);
    }

    //Encryption Operations
    public byte[] encrypt(PrivateKey privateKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(message.getBytes());
    }

    //UTF-8 Bytes to string operations
    public String byte_to_string(byte[] decrypted_bytes){
        return new String(decrypted_bytes, StandardCharsets.UTF_8);
    }

    //Send Json Package To Client
    public final void send_json_package(String json_string) throws IOException {
        this.stream_out.writeUTF(json_string);
    }

    //Json Array to byte[]
    private byte[] json_array_to_byte_array(JSONArray array){
        byte[] bytes = new byte[array.length()];
        for (int index = 0; index < array.length(); index++) {
            bytes[index] = (byte) array.get(index);
        }
        return bytes;
    }


}
