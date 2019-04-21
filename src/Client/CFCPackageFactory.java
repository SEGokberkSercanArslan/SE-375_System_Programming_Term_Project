package Client;

import org.json.JSONObject;

public class CFCPackageFactory {

    public CFCPackageFactory(){

    }

    //RSA Public Key Request
    protected final String rsa_public_key_request(){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","RSA-PUB");
        return object.toString();
    }

    //Sign-Up Request
    protected final String sign_up_request(String username,byte[] password,byte[] secretQuestion,byte[] answer){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","Sign-Up");
        object.put("Username",username);
        object.put("Password",password);
        object.put("SecretQuestion",secretQuestion);
        object.put("Answer",answer);
        return object.toString();
    }

    //Sing-In Request
    protected final String sign_in_request(String username,byte[] password){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","Sing-Up");
        object.put("Username",username);
        object.put("Password",password);
        return object.toString();
    }

    //Forget Password Request
    //Phase 1 Package
    protected final String forget_password_request_phase_1(String username){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","Forget-Password-Phase-1");
        object.put("Username",username);
        return object.toString();
    }
    //Phase 2 Package
    protected final String forget_password_request_phase_2(String username,byte[] answer){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","Forget-Password-Phase-2");
        object.put("Username",username);
        object.put("Answer",answer);
        return object.toString();
    }


}
