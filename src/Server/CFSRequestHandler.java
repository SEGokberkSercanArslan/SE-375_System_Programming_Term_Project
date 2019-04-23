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
    private CFSDatabase database = new CFSDatabase();

    /*Constructor Decelerations*/
    public CFSRequestHandler(Socket client, CFServer server){
        this.client = client;
        this.server = server;
        try {
            this.stream_input = new DataInputStream(client.getInputStream());
            this.stream_out = new DataOutputStream(client.getOutputStream());
            this.cfsClient = new CFSClient(client,privateKey(keyGenerator()),publicKey(keyGenerator()));
            System.out.println("In request Handler");
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!this.client.isClosed()){
            try {
                String request = stream_input.readUTF();
                decode_request(request);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    client.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }finally {

            }
        }
    }

    /*Function Decelerations*/
    public void decode_request(String request) throws Exception {

        JSONObject object = new JSONObject(request);

        if (object.get("Type").toString().equals("Request")){
            switch (object.get("Request").toString()) {
                case "RSA-PUB":
                    server.add_active_client(this.cfsClient);
                    send_json_package(factory.rsa_public_key_package(cfsClient.getPublicKey()));
                    break;
                case "Sign-Up": {
                    String username = object.get("Username").toString();
                    if (database.is_user_exist(username)) {
                        send_json_package(factory.error_username_already_exists());
                    } else {
                        String password = byte_to_string(decrypt(this.cfsClient.getPrivateKeyClassVersion(), json_array_to_byte_array((JSONArray) object.get("Password"))));
                        String secretQuestion = byte_to_string(decrypt(this.cfsClient.getPrivateKeyClassVersion(), json_array_to_byte_array((JSONArray) object.get("SecretQuestion"))));
                        String secretAnswer = byte_to_string(decrypt(this.cfsClient.getPrivateKeyClassVersion(), json_array_to_byte_array((JSONArray) object.get("Answer"))));
                        database.add_new_user(username, password, secretQuestion, secretAnswer);
                        send_json_package(factory.confirmation_package_sign_up());
                    }

                    break;
                }
                case "Sign-In":{
                    String username = object.get("Username").toString();
                    String password = byte_to_string(decrypt(this.cfsClient.getPrivateKeyClassVersion(),json_array_to_byte_array((JSONArray) object.get("Password"))));
                    if (server.is_username_online(username)){
                        // Create Package Account Already Online
                    }else {
                        if (password.equals(database.get_user_password(username))){

                        }
                        else {

                        }
                    }
                }

                    break;
                case "Forget-Password-Phase-1": {
                    String username = object.get("Username").toString();
                    if (database.is_user_exist(username)){
                        send_json_package(factory.phase_1_password_reset_response_package(encrypt(this.cfsClient.getPrivateKeyClassVersion(),database.get_secret_question(username))));
                    }else {
                        send_json_package(factory.phase_1_password_reset_invalid_username_package());
                    }
                    break;
                }
                case "Forget-Password-Phase-2": {
                    String username = object.get("Username").toString();
                    String answer = byte_to_string(decrypt(this.cfsClient.getPrivateKeyClassVersion(), json_array_to_byte_array((JSONArray) object.get("Answer"))));
                    if (database.get_secret_answer(username).equals(answer)){
                        send_json_package(factory.phase_2_password_reset_response_package(encrypt(this.cfsClient.getPrivateKeyClassVersion(),database.get_user_password(username))));
                    }else {
                        send_json_package(factory.phase_2_password_reset_invalid_answer_package());
                    }
                    break;
                }
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
