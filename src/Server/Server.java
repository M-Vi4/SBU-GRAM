package Server;

import Common.*;

import java.util.Map;

public class Server {
    public static final int PORT = 2222;
    private static boolean isServerUp = true;

    public static Map<String , User> users = null;

    public boolean isIsServerUp(){
        return isServerUp;
    }

    public static void main(String[] args) {

    }
}
