/*
 * Copyright (c) 2019. This file created by Gökberk Sercan Arslan. All Rights Reserved.
 */

package Server.Game;

import Client.CFClient;
import Server.CFSClient;

import java.util.ArrayList;

public class CFSeason {

    /*Global variable declarations here*/
    private String season_name;
    private ArrayList<CFSClient> players = new ArrayList<CFSClient>();
    private int max_players;

    /*Constructors declaration here*/
    public CFSeason(String season_name, int max_players){
        this.season_name = season_name;
        this.max_players = max_players;
    }

    public void add_player(CFSClient player){
        if (players.size() <= max_players){
            players.add(player);
        }
    }

    public String getSeason_name() {
        return season_name;
    }

    public ArrayList<CFSClient> getPlayers() {
        return players;
    }

    public int getMax_players() {
        return max_players;
    }
    /*Function definitions here*/

}
