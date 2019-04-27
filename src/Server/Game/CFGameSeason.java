package Server.Game;

import java.util.ArrayList;

public class CFGameSeason {

    /*Global variable declarations here*/
    private String  season_name;
    private CFAdmin season_admin;
    private ArrayList<CFPlayer> players = new ArrayList<CFPlayer>();
    private int max_players;

    /*Constructors declaration here*/
    public CFGameSeason(String season_name,CFAdmin admin,int max_players){
        this.season_name = season_name;
        this.season_admin= admin;
        this.max_players = max_players;
    }

    public void add_player(CFPlayer player){
        if (players.size() < max_players){
            players.add(player);
        }
    }

    public int get_current_number_of_players(){
        return players.size();
    }

    public int get_max_number_of_players(){
        return max_players;
    }

    public String get_season_name(){
        return season_name;
    }


    /*Function definitions here*/

}
