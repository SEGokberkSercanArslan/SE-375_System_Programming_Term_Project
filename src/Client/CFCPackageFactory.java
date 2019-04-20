package Client;

import org.json.JSONObject;

public class CFCPackageFactory {

    public CFCPackageFactory(){

    }

    //RSA Public Key Request
    protected String rsa_public_key_request(){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","RSA-PUB");
        return object.toString();
    }


}
