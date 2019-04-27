package Server.Game;

import java.net.Socket;

public class CFPlayer {

    /*Global variable declarations here*/
    private String username;
    private Socket client;

    /*Constructors declaration here*/
    public  CFPlayer(String username, Socket client){
        this.username = username;
        this.client = client;
    }

    /*Function definitions here*/

}
