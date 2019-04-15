package Server;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class CFSRequestHandler implements Runnable{

    /*Global Variables*/
    private Socket client;
    private DataInputStream stream;

    /*Constructor Decelerations*/
    public CFSRequestHandler(Socket client){
        this.client = client;
        try {
            this.stream = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!this.client.isClosed()){
            try {
                String request = stream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*Function Decelerations*/
    public void decode_request(String request){

        JSONObject object = new JSONObject(request);
        String type = (String) object.get("Type");

        if (type.equals("Request")){

        }

    }

}
