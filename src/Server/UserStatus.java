/*
 * Copyright (c) 2019. This file created by Gökberk Sercan Arslan. All Rights Reserved.
 */

package Server;

public class UserStatus {

    //Variable Decelerations
    private static boolean kicked = false;
    private static boolean out = false;
    private static boolean status_thread = true;

    /*Constructor*/
    public UserStatus() {

    }


    /*Getter and Setter methods*/
    public static boolean isKicked() {
        return kicked;
    }

    public static void setKicked(boolean kicked) {
        UserStatus.kicked = kicked;
    }

    public static boolean isOut() {
        return out;
    }

    public static void setOut(boolean out) {
        UserStatus.out = out;
    }

    public static boolean isStatus_thread() {
        return status_thread;
    }

    public static void setStatus_thread(boolean status_thread) {
        UserStatus.status_thread = status_thread;
    }
}
