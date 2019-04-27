package Server;

import Server.Game.CFGameSeason;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CFSPackageFactory {



    public CFSPackageFactory(){

    }

    //Create this package if client socket is same that means online
    protected final String error_already_online(){
        JSONObject object = new JSONObject();
        object.put("Type","Error");
        object.put("Error","Already-Online");
        return object.toString();
    }

    //Create This package if user is already sign - in
    protected final String error_username_online(){
        JSONObject object = new JSONObject();
        object.put("Type","Error");
        object.put("Error","Username-Already-Sign-In");
        return object.toString();
    }

    protected final String error_invalid_username_password(){
        JSONObject object = new JSONObject();
        object.put("Type","Error");
        object.put("Error","Invalid-Username-Password");
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
    protected final String rsa_public_key_package(String pubKey){
        System.out.println(pubKey);
        JSONObject object = new JSONObject();
        object.put("Type","Response");
        object.put("Response","RSA-PUB");
        object.put("RSA-PUB",pubKey);
        return object.toString();
    }

    //Phase 1 Password Reset Request Response
    //Confirmation
    protected final String phase_1_password_reset_response_package(String secretQuestion){
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
    protected final String phase_2_password_reset_response_package(String password){
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
    protected final String confirmation_package_sign_in(ArrayList<CFGameSeason> seasons){
        JSONObject object = new JSONObject();
        object.put("Type","Confirmation");
        object.put("Confirmation","Sign-In");
        //Main menu list will come here in Json Array
        JSONArray array = new JSONArray();
        for (CFGameSeason season : seasons) {
            array.put(new JSONObject().put("Season", season.get_season_name()).put("Maximum", season.get_max_number_of_players()).put("Current", season.get_current_number_of_players()));
        }
        object.put("Seasons",array);
        return object.toString();
    }

    //Sign-Out Confirmation
    protected final String confirmation_sign_out(){
        JSONObject object = new JSONObject();
        object.put("Type","Confirmation");
        object.put("Confirmation","Sign-Out");
        return object.toString();
    }

    //Refresh Lobby Request Response
    protected final String refresh_lobby_response_package(ArrayList<CFGameSeason> seasons){
        JSONObject object = new JSONObject();
        object.put("Type","Response");
        object.put("Response","Refresh");
        JSONArray array = new JSONArray();
        for (CFGameSeason season : seasons) {
            array.put(new JSONObject().put("Season", season.get_season_name()).put("Maximum", season.get_max_number_of_players()).put("Current", season.get_current_number_of_players()));
        }
        object.put("Seasons",array);
        return object.toString();
    }







}
