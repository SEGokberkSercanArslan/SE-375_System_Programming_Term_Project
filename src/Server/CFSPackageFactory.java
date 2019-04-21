package Server;

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

    //Phase 1 Password Reset Request Response
    //Confirmation
    protected final String phase_1_password_reset_response_package(byte[] secretQuestion){
        JSONObject object = new JSONObject();
        object.put("Type","Response");
        object.put("Response","Forget-Password-Phase-1");
        object.put("SecretQuestion",secretQuestion);
        return object.toString();
    }

    //Error
    protected final String phase_1_password_reset_invalid_username_package(){
        JSONObject object = new JSONObject();
        object.put("Type","Error");
        object.put("Error","Invalid Username");
        return object.toString();
    }

    //Phase 2 Password Reset Request Responses
    //Confirmation
    protected final String phase_2_password_reset_response_package(byte[] password){
        JSONObject object = new JSONObject();
        object.put("Type","Response");
        object.put("Response","Forget-Password-Phase-2");
        object.put("Password",password);
        return object.toString();
    }

    //Error Invalid Answer
    protected final String phase_2_password_reset_invalid_answer_package(){
        JSONObject object = new JSONObject();
        object.put("Type","Error");
        object.put("Error","Invalid Answer");
        return object.toString();
    }

    //Sign-Up Confirmation
    protected final String confirmation_package_sign_up(){
        JSONObject object = new JSONObject();
        object.put("Type","Confirmation");
        object.put("Confirmation","Sign-Up");
        return object.toString();
    }

    //Sign-In
    protected String confirmation_package_sign_in(){
        JSONObject object = new JSONObject();
        object.put("Type","Confirmation");
        object.put("Confirmation","Sign-In");
        //Main menu list will come here in Json Array
        return object.toString();
    }




}
