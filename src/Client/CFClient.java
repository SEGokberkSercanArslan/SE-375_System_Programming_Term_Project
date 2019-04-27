/*
 * Copyright (c) 2019. This file created by Gökberk Sercan Arslan. All Rights Reserved.
 */

package Client;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.Cipher;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;

public class CFClient {

    /*Global variable declarations here*/
    private boolean is_active = false;
    private Socket client_socket;
    private DataOutputStream out;
    private DataInputStream input;
    private PublicKey publicKey = null;
    private CFCPackageFactory factory;

    /*Constructors declaration here*/
    private CFClient(){
        try {
            System.out.println(InetAddress.getLocalHost().getHostAddress());
            client_socket = new Socket(InetAddress.getLocalHost().getHostAddress(),12600);
            input = new DataInputStream(client_socket.getInputStream());
            out = new DataOutputStream(client_socket.getOutputStream());
            factory = new CFCPackageFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*Main programme declaration here*/
    public static void main(String[] args) throws Exception {
        CFClient client = new CFClient();
        client.start();
    }

    /*Function definitions here*/

    private void start() throws Exception {
        is_active = true;
        while (is_active){
            Scanner scanner = new Scanner(System.in);
            System.out.println("0 : Sign-in game");
            System.out.println("1 : Sign-up game");
            System.out.println("2 : Forget Password");
            System.out.println("3 : Close the game");
            int choice = scanner.nextInt();
            switch (choice){
                case 0:sign_in();
                    break;
                case 1:sign_up();
                    break;
                case 2:forget_password();
                    break;
                case 3:is_active = false;
                    break;
                default: System.out.println("Wrong Input");
            }
        }
        System.out.println("Thanks for playing Collect Four.");
    }

    private void sign_in() throws Exception {
        get_public_key_if_null();
        //Need a Screen Flush Function Here
        String username;
        String password;
        Scanner scanner = new Scanner(System.in);
        {
            System.out.print("Username : ");
            username = scanner.next();
            System.out.print("Password : ");
            password = scanner.next();
        }
        send_json_package(factory.sign_in_request(username,base64_string_converter(encrypt(publicKey,password))));
        String response = input.readUTF();
        JSONObject object = new JSONObject(response);
        if (object.get("Type").toString().equals("Confirmation")){
            if (object.get("Confirmation").toString().equals("Sign-In")){
                game_lobby((object.getJSONArray("Seasons")));
            }
        }else if (object.get("Type").toString().equals("Error")){
            System.out.println(object.get("Error").toString());
        }
    }

    private void game_lobby(JSONArray seasons) throws IOException {
        boolean sign_in = true;
        JSONArray season_list = seasons;
        while (sign_in){
            System.out.println("Active Server.Game Seasons Listed Bellow");
            for (int index = 0; index < season_list.length(); index++) {
                System.out.println("ID : " + index + " Server.Game : " + season_list.getJSONObject(index).get("Season").toString() +
                        " Current Players : " + season_list.getJSONObject(index).get("Current").toString() +
                        " Maximum : " + season_list.getJSONObject(index).get("Maximum").toString() );
            }
            System.out.println("For Sign-Out input : 'sign-out' ");
            System.out.println("For Refresh Lobby input : 'refresh'");
            System.out.println("For Create Server input : 'create'");
            System.out.println("For Join Server input : Server ID");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            if (StringUtils.isNumeric(choice)){
                //Server Join Request Here

            }else if (choice.equals("sign-out")){
                //Sign Out Request Here
                send_json_package(factory.sign_out_request());
                String confirmation = input.readUTF();
                JSONObject object = new JSONObject(confirmation);
                if (object.get("Type").toString().equals("Confirmation")){
                    if (object.get("Confirmation").toString().equals("Sign-Out")){
                        sign_in = false;
                        System.out.println("Sign-Out Complete.");
                    }else {
                        System.out.println("Wrong Type of Confirmation");
                    }
                }

            }else if (choice.equals("refresh")){
                //Refresh Request Here
                send_json_package(factory.refresh_lobby_request());
                String response = input.readUTF();
                JSONObject object = new JSONObject(response);
                if (object.get("Type").toString().equals("Response")){
                    if (object.get("Response").toString().equals("Refresh")){
                        season_list = object.getJSONArray("Seasons");
                    }else {
                        System.out.println("Wrong Response Type");
                    }
                }

            }else if (choice.equals("create")){
                System.out.print("Season name : ");
                String season_name = scanner.nextLine();
                System.out.print("Season Capacity min 4 player required : ");
                String season_cap = scanner.nextLine();
                int capacity;
                if (StringUtils.isNumeric(season_cap)){
                    capacity = Integer.parseInt(season_cap);
                    if (capacity >= 4){
                        send_json_package(factory.create_season_request(season_name,capacity));
                        String response = input.readUTF();
                        JSONObject object = new JSONObject(response);
                        if (object.get("Type").toString().equals("Confirmation")){
                            if (object.get("Confirmation").toString().equals("Create-Season")){
                                in_game_season(object,true);
                            }
                        }

                    }else {
                        System.out.println("Wrong Input Type");
                    }
                }else {
                    System.out.println("Wrong Input Type");
                }
            }else {
                System.out.println("Wrong Input Type");
            }
        }
    }

    private void in_game_season(JSONObject object, boolean admin_access){
        boolean in_season = true;
        while (in_season){
            if (admin_access){ // If user has admin Access

            }else { // If

            }
        }
    }

    private void sign_up() throws Exception {
        get_public_key_if_null();
        //Need a Screen Flush Function Here
        String username;
        String password;
        String secretQuestion;
        String secretAnswer;
        Scanner scanner = new Scanner(System.in);
        Scanner lineScanner = new Scanner(System.in);
        {
            System.out.print("Please select a Username : ");
            username = scanner.next();
            System.out.print("Please select a Password : ");
            password = scanner.next();
            System.out.print("Please select a Secret Question : ");
            secretQuestion = lineScanner.nextLine();
            System.out.print("Please select Secret Question's Answer : ");
            secretAnswer = lineScanner.nextLine();
        }// Collect Form Information in This Scope
        send_json_package(factory.sign_up_request(username,base64_string_converter(encrypt(publicKey,password)),base64_string_converter(encrypt(publicKey,secretQuestion)),base64_string_converter(encrypt(publicKey,secretAnswer))));
        // Check Server Response if username exists or not
        JSONObject object = new JSONObject(input.readUTF());
        boolean handled_in_error = false;
        while (object.get("Type").toString().equals("Error")){
            System.out.println("Username already exists please choose another one.");
            System.out.println("For Abort this sign-up process please input : 1");
            System.out.print("Please select a Username : ");
            username = scanner.next();
            if (username.equals("1")){
                handled_in_error = true;
                break;
            }else {
                send_json_package(factory.sign_up_request(username,base64_string_converter(encrypt(publicKey,password)),base64_string_converter(encrypt(publicKey,secretQuestion)),base64_string_converter(encrypt(publicKey,secretAnswer))));
                JSONObject response = new JSONObject(input.readUTF());
                if (response.get("Type").toString().equals("Confirmation")){
                    if (response.get("Confirmation").toString().equals("Sign-Up")){
                        System.out.println("Your account created successfully");
                        handled_in_error = true;
                        break;
                    }
                }
            }
        }
        if (!handled_in_error){
            if (object.get("Type").toString().equals("Confirmation")){
                if (object.get("Confirmation").toString().equals("Sign-Up")){
                    System.out.println("Your account created successfully");
                }
            }
        }
    }

    private void forget_password() throws Exception {
        get_public_key_if_null();
        String username;
        String secretAnswer;
        Scanner scanner = new Scanner(System.in);
        Scanner lineScanner = new Scanner(System.in);
        System.out.print("Please input Username : ");
        username = scanner.next();
        send_json_package(factory.forget_password_request_phase_1(username));
        JSONObject phase_1_response = new JSONObject(input.readUTF());
        if (phase_1_response.get("Type").toString().equals("Response")){
            if (phase_1_response.get("Response").toString().equals("Forget-Password-Phase-1")){
                System.out.println("Question : " + byte_to_string(decrypt(publicKey,base64_byte_converter(phase_1_response.get("SecretQuestion").toString()))));
                System.out.print("Answer : ");
                String answer = lineScanner.nextLine();
                send_json_package(factory.forget_password_request_phase_2(username,base64_string_converter(encrypt(publicKey,answer))));
                JSONObject phase_2_response = new JSONObject(input.readUTF());
                if (phase_2_response.get("Type").toString().equals("Response")){
                    if (phase_2_response.get("Response").toString().equals("Forget-Password-Phase-2")){
                        System.out.println("Your password is : " + byte_to_string(decrypt(publicKey,base64_byte_converter(phase_2_response.get("Password").toString()))));
                    }
                }
                else if (phase_2_response.get("Type").toString().equals("Error")){
                    System.out.println(phase_2_response.get("Error").toString());
                }
            }
        }else if (phase_1_response.get("Type").toString().equals("Error")){
            System.out.println(phase_1_response.get("Error").toString());
        }
    }

    private void send_json_package(String json_package) throws IOException {
        this.out.writeUTF(json_package);
    }

    private void get_public_key_if_null() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, Base64DecodingException {
        if (this.publicKey == null){
            System.out.println("In Public Key Null");
            send_json_package(factory.rsa_public_key_request());
            String json_string = input.readUTF();
            JSONObject object = new JSONObject(json_string);
            System.out.println(object.toString());
            if (object.get("Type").toString().equals("Response")){
                if (object.get("Response").toString().equals("RSA-PUB")){
                    byte[] keyPub = base64_byte_converter(object.get("RSA-PUB").toString());                                        //Modify base 64 decoder
                    publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(keyPub));
                    System.out.println("Public Key Done");
                }
            }
        }
    }

    //Encryption Operations
    private byte[] encrypt(PublicKey publicKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
    }

    //Decipher Operations
    private byte[] decrypt(PublicKey publicKey, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(encrypted);
    }

    //UTF-8 Bytes to string operations
    private String byte_to_string(byte[] decrypted_bytes){
        return new String(decrypted_bytes,StandardCharsets.UTF_8);
    }

    /*Special Methods Converting Bytes to String*/
    private String base64_string_converter(byte[] bytes){
        return Base64.encode(bytes);
    }
    private byte[] base64_byte_converter(String msg) throws Base64DecodingException {
        return Base64.decode(msg);
    }

}

