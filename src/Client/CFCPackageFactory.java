/*
 * Copyright (c) 2019. This file created by Gökberk Sercan Arslan. All Rights Reserved.
 */

package Client;

import org.apache.commons.lang3.StringUtils;
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
    protected final String sign_up_request(String username,String password,String secretQuestion,String answer){
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
    protected final String sign_in_request(String username,String password){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","Sign-In");
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
    protected final String forget_password_request_phase_2(String username,String answer){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","Forget-Password-Phase-2");
        object.put("Username",username);
        object.put("Answer",answer);
        return object.toString();
    }

    //Sign-out Request
    protected final String sign_out_request(){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","Sign-Out");
        return object.toString();
    }

    //Refresh Lobby Request
    protected final String refresh_lobby_request(){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","Refresh-Lobby");
        return object.toString();
    }

    //Create Season Request
    protected final String create_season_request(String server_name,int capacity){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","Create-Season");
        object.put("Season-Name",server_name);
        object.put("Season-Capacity",capacity);
        return object.toString();
    }

    //Refresh Season Request
    protected final String refresh_season_request(String season_name){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","Refresh-Season");
        object.put("Refresh-Season",season_name);
        return object.toString();
    }

    //Season Start Request
    protected final String start_season_request(String season_name){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","Start-Game");
        object.put("Start-Game",season_name);
        return object.toString();
    }

    //Kick Player From Season
    protected final String kick_player_request(String username){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","Kick-Player");
        object.put("Kick-Player",username);
        return object.toString();
    }

    //Close Season Kicked All Players
    protected final String close_season_request(){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","Close-Season");
        return object.toString();
    }

    //Join Server Request
    protected final String join_server_request(String server_name){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","Join-Server");
        object.put("Join-Server",server_name);
        return object.toString();
    }

    //Client exit game season (Joined Client)
    protected final String leave_server_request(){
        JSONObject object = new JSONObject();
        object.put("Type","Request");
        object.put("Request","Leave-Server");
        return object.toString();
    }




}
