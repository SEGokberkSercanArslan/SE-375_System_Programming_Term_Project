package Server;

import org.json.JSONArray;
import org.json.JSONObject;

public class CFSPackageFactory {



    public CFSPackageFactory(){

    }

    //Create this package if client already online
    protected final String error_already_online(){
        JSONObject object = new JSONObject();
        object.put("Type","Error");
        object.put("Error","Already-Online");
        return object.toString();
    }

    //Create this package if username exists
    protected final String error_username_already_exists(){
        JSONObject object = new JSONObject();
        object.put("Type","Error");
        object.put("Error","Username-Exists");
        return object.toString();
    }

    //Create this package to send client his/her public key
    protected final String rsa_public_key_package(byte[] pubKey){
        JSONObject object = new JSONObject();
        object.put("Type","Response");
        object.put("Response","RSA-PUB");
        object.put("RSA-PUB",pubKey);
        return object.toString();
    }

    //Sign-up Confirmation
    protected final String confirmation_package_sign_up(){
        JSONObject object = new JSONObject();
        object.put("Type","Confirmation");
        object.put("Confirmation","Sign-Up");
        return object.toString();
    }




}
