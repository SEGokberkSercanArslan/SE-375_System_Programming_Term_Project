package Server;

import org.json.JSONObject;

public class CFSPackageFactory {



    public CFSPackageFactory(){

    }

    protected final String error_already_online(){
        JSONObject object = new JSONObject();
        object.put("Type","Error");
        object.put("Error","Already-Online");
        return object.toString();
    }


}
