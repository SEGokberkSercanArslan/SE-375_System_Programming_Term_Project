package Server.Game;

import java.util.ArrayList;
import java.util.Collections;

public class CFGame {

    /*Global variable declarations here*/
    private ArrayList<CFPlayer> players;
    private ArrayList<Integer> numbers = new ArrayList<Integer>();

    /*Constructors declaration here*/
    public CFGame(ArrayList<CFPlayer> players){
        this.players = players;
        create_numbers(players.size());
    }

    /*Function definitions here*/
    private void create_numbers(int number_of_players){
        for (int i = 0; i < number_of_players; i++) {
            for (int j = 0; j < 4; j++) {
                this.numbers.add(i);
            }
        }
        Collections.shuffle(this.numbers);
    }



}
