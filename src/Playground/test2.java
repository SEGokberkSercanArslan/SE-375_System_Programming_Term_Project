package Playground;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;

public class test2 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
       /*
        byte[] arr = new byte[]{1,2,3,4,5,6,7};
        JSONObject obj = new JSONObject();
        obj.put("arr",arr);
        String string = obj.toString();
        JSONObject obj2 = new JSONObject(string);

        if (obj2.get("arr") instanceof JSONArray){
            System.out.println("Json Arr");
        }else if (obj2.get("arr") instanceof byte[]){
            System.out.println("Byte Arr");
        }
        else {
            System.out.println("Not Instance");
        }
    */

       JSONObject object = new JSONObject();
       object.put("Key","RSA-PUB");
       if (object.get("Key").toString().equals("RSA-PUB")){
           System.out.println("True");
       }
    }

}
