package Client;

import java.util.Scanner;

public class CFClient {

    /*Global variable declarations here*/
    private boolean is_active = false;

    /*Constructors declaration here*/
    private CFClient(){

    }

    /*Main programme declaration here*/
    public static void main(String[] args) {
        CFClient client = new CFClient();
        client.start();
    }

    /*Function definitions here*/

    private void start(){
        is_active = true;
        while (is_active){
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice){
                case 0:sign_in();
                    break;
                case 1:sign_up();
                    break;
                case 2: is_active = false;
                    break;
                default: System.out.println("Wrong Input");
            }
        }
        System.out.println("Thanks for playing Collect Four.");
    }

    private void sign_in(){

    }

    private void sign_up(){

    }

}
