/*
 * Copyright (c) 2019. This file created by Gökberk Sercan Arslan. All Rights Reserved.
 */

package Server;

import Server.Game.CFSeason;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.json.JSONObject;

import javax.crypto.Cipher;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public class CFSRequestHandler implements Runnable{

    /*Global Variables*/
    private Socket client;
    private CFServer server;
    private DataInputStream stream_input;
    private DataOutputStream stream_out;
    private CFSClient cfsClient;
    private CFSPackageFactory factory = new CFSPackageFactory();
    private CFSDatabase database = new CFSDatabase();
    private CFSeason season;

    /*Constructor Decelerations*/
    public CFSRequestHandler(Socket client, CFServer server){
        this.client = client;
        this.server = server;
        try {
            this.stream_input = new DataInputStream(client.getInputStream());
            this.stream_out = new DataOutputStream(client.getOutputStream());
            this.cfsClient = new CFSClient(client,keyGenerator());
            System.out.println("In request Handler");
        } catch (IOException | NoSuchAlgorithmException e) {
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
                case "RSA-PUB":{
                    server.add_active_client(this.cfsClient);
                    send_json_package(factory.rsa_public_key_package(base64_string_converter(cfsClient.getPublicKey())));
                    break;
                }
                case "Sign-Up": {
                    String username = object.get("Username").toString();
                    if (database.is_user_exist(username)) {
                        send_json_package(factory.error_username_already_exists());
                    } else {
                        System.out.println("---------------------------");
                        System.out.println(object.get("Password").toString());
                        String password = byte_to_string(decrypt(this.cfsClient.getPrivateKeyClassVersion(), base64_byte_converter(object.get("Password").toString())));
                        String secretQuestion = byte_to_string(decrypt(this.cfsClient.getPrivateKeyClassVersion(), base64_byte_converter(object.get("SecretQuestion").toString())));
                        String secretAnswer = byte_to_string(decrypt(this.cfsClient.getPrivateKeyClassVersion(), base64_byte_converter(object.get("Answer").toString())));
                        database.add_new_user(username, password, secretQuestion, secretAnswer);
                        send_json_package(factory.confirmation_package_sign_up());
                    }
                    break;
                }
                case "Sign-In":{
                    String username = object.get("Username").toString();
                    String password = byte_to_string(decrypt(this.cfsClient.getPrivateKeyClassVersion(),base64_byte_converter(object.get("Password").toString())));
                    if (server.is_username_online(username)){
                        // Create Package Account Already Online
                        send_json_package(factory.error_username_online());
                    }else {
                        if (password.equals(database.get_user_password(username))){
                            this.cfsClient.set_sign_in(true);
                            this.cfsClient.setUsername(username);
                            send_json_package(factory.confirmation_package_sign_in(server.get_Seasons())); //Send Server List to User
                        }
                        else {
                            //Password or Username Invalid
                            send_json_package(factory.error_invalid_username_password());
                        }
                    }
                    break;
                }

                case "Forget-Password-Phase-1": {
                    String username = object.get("Username").toString();
                    if (database.is_user_exist(username)){
                        send_json_package(factory.phase_1_password_reset_response_package(base64_string_converter(encrypt(this.cfsClient.getPrivateKeyClassVersion(),database.get_secret_question(username)))));
                    }else {
                        send_json_package(factory.phase_1_password_reset_invalid_username_package());
                    }
                    break;
                }
                case "Forget-Password-Phase-2": {
                    String username = object.get("Username").toString();
                    String answer = byte_to_string(decrypt(this.cfsClient.getPrivateKeyClassVersion(),base64_byte_converter(object.get("Answer").toString())));
                    if (database.get_secret_answer(username).equals(answer)){
                        send_json_package(factory.phase_2_password_reset_response_package(base64_string_converter(encrypt(this.cfsClient.getPrivateKeyClassVersion(),database.get_user_password(username)))));
                    }else {
                        send_json_package(factory.phase_2_password_reset_invalid_answer_package());
                    }
                    break;
                }
                case "Sign-Out":{ // CFS Client Request
                    this.cfsClient.set_sign_in(false);
                    send_json_package(factory.confirmation_sign_out());
                    break;
                }
                case "Refresh-Lobby":{
                    send_json_package(factory.refresh_lobby_response_package(server.get_Seasons()));
                    break;
                }
                case "Create-Season":{ //For Admin Request
                    String season_name = object.get("Season-Name").toString();
                    String season_cap = object.get("Season-Capacity").toString();
                    int season_capacity = Integer.parseInt(season_cap);
                    season = new CFSeason(season_name,season_capacity);
                    this.cfsClient.setAdmin_access(true);
                    season.add_player(this.cfsClient);
                    this.server.get_Seasons().add(season);
                    send_json_package(factory.confirmation_create_season(season));
                    break;
                }
                case "Refresh-Season":{
                    send_json_package(factory.refresh_season_response(season));
                    break;
                }
                case "Kick-Player":{ //For Admin Request
                    //Fill Here
                    break;
                }
                case "Start-Game":{ //For Admin Request
                    //Fill Here
                    break;
                }
                case "Join-Server":{ //For Player Request
                    //Fill Here
                    break;
                }
                case "Leave-Server":{ //For Player Request
                    //Fill Here
                    break;
                }
            }
        }

    }

    /*RSA KEY GENERATORS*/
    private KeyPair keyGenerator() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        return keyPair;
    }

    private byte [] publicKey(KeyPair keyPairGenerator){
        return keyPairGenerator.getPublic().getEncoded();
    }

    private byte [] privateKey(KeyPair keyPairGenerator){
        return keyPairGenerator.getPrivate().getEncoded();
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

    /*Special Methods Converting Bytes to String*/
    private String base64_string_converter(byte[] bytes){
        return Base64.encode(bytes);
    }
    private byte[] base64_byte_converter(String msg) throws Base64DecodingException {
        return Base64.decode(msg);
    }


}
