/*
 * Copyright (c) 2019. This file created by Gökberk Sercan Arslan. All Rights Reserved.
 */

package Server;

import Server.Game.CFSeason;
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
    protected final String confirmation_package_sign_in(ArrayList<CFSeason> seasons){
        JSONObject object = new JSONObject();
        object.put("Type","Confirmation");
        object.put("Confirmation","Sign-In");
        //Main menu list will come here in Json Array
        JSONArray array = new JSONArray();
        for (CFSeason season : seasons) {
            array.put(new JSONObject().put("Season", season.getSeason_name()).put("Maximum", season.getMax_players()).put("Current", season.getPlayers().size()));
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
    protected final String refresh_lobby_response_package(ArrayList<CFSeason> seasons){
        JSONObject object = new JSONObject();
        object.put("Type","Response");
        object.put("Response","Refresh");
        JSONArray array = new JSONArray();
        for (CFSeason season : seasons) {
            array.put(new JSONObject().put("Season", season.getSeason_name()).put("Maximum", season.getMax_players()).put("Current", season.getPlayers().size()));
        }
        object.put("Seasons",array);
        return object.toString();
    }

    //Create Season Response
    protected final String confirmation_create_season(CFSeason season){
        JSONObject object = new JSONObject();
        object.put("Type","Confirmation");
        object.put("Confirmation","Create-Season");
        object.put("Season-Name",season.getSeason_name());
        JSONArray array = new JSONArray();
        for (int index = 0; index < season.getPlayers().size(); index++) {
            array.put(new JSONObject().put("ID",index).put("Player",season.getPlayers().get(index).getUsername()));
        }
        object.put("Season-Players",array);
        return object.toString();
    }

    //Refresh Season Response
    protected final String refresh_season_response(CFSeason season){
        JSONObject object = new JSONObject();
        object.put("Type","Response");
        object.put("Response","Refresh-Season");
        object.put("Season-Name",season.getSeason_name());
        JSONArray array = new JSONArray();
        for (int index = 0; index < season.getPlayers().size(); index++) {
            array.put(new JSONObject().put("ID",index).put("Player",season.getPlayers().get(index).getUsername()));
        }
        object.put("Season-Players",array);
        return object.toString();
    }

    //Player Kicked Notification
    protected final String player_kicked_notification(){
        JSONObject object = new JSONObject();
        object.put("Type","Notification");
        object.put("Notification","Kicked");
        object.put("Kicked","You Were Kicked by Season Admin");
        return object.toString();
    }

    //Season Closed Confirmation
    protected final String season_close_confirmation(){
        JSONObject object = new JSONObject();
        object.put("Type","Confirmation");
        object.put("Confirmation","Close-Season");
        return object.toString();
    }








}
