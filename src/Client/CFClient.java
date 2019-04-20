package Client;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.Cipher;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
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
    public static void main(String[] args) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        CFClient client = new CFClient();
        client.start();
    }

    /*Function definitions here*/

    private void start() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        is_active = true;
        while (is_active){
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            System.out.println("0 : Sign-in game");
            System.out.println("1 : Sign-up game");
            System.out.println("2 : Close the game");
            switch (choice){
                case 0:sign_in();
                    break;
                case 1:sign_up();
                    break;
                case 2: is_active = false;
                    break;
                default: System.out.println("Wrong Input");
            }
        }
        System.out.println("Thanks for playing Collect Four.");
    }

    private void sign_in() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        get_public_key_if_null();
    }

    private void sign_up() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        get_public_key_if_null();
    }

    private void forget_password() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        get_public_key_if_null();
    }

    private void send_json_package(String json_package) throws IOException {
        this.out.writeUTF(json_package);
    }

    private void get_public_key_if_null() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        if (this.publicKey == null){
            send_json_package(factory.rsa_public_key_request());
            String json_string = input.readUTF();
            JSONObject object = new JSONObject(json_string);
            if (object.get("Type").toString().equals("Response")){
                if (object.get("Response").toString().equals("RSA-PUB")){
                    JSONArray array = new JSONArray(object.get("RSA-PUB"));
                    byte[] keyPub = new byte[array.length()];
                    for (int index = 0; index < array.length(); index++) {
                        keyPub[index] = (byte) array.get(index);
                    }
                    publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(keyPub));
                }
            }
        }
    }

    //Encryption Operations
    public byte[] encrypt(PublicKey publicKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes());
    }

}
