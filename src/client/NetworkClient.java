package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;

public class NetworkClient{

    private DataInputStream input;
    private DataOutputStream output;

    private boolean loggedIn = false;
    //Ziel ist es, die Daten hier zwischenzuspeichern. Somit muss in der GamePlay Klasse nur noch Darstellung und Steuerung der Schlange realisiert werden.
    private Map<String, float[]> playerData;

    public NetworkClient() {

        try{
            Socket socket = new Socket("localhost", 3337);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            Login loginDialog = new Login();
            loginDialog.addLoginObserver(this);

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This function is called when a player has successfully logged in to the server.
     *
     * @param loginName this is the name, the user has used to login
     */
    public void hasLoggedIn(String loginName){
        this.loggedIn = true;
        System.out.println("Player --->"+ loginName);
        this.sendData(loginName);
    }

    /**
     * Provides information about whether the user has already logged on to the server.
     *
     * @return true if the user is logged in
     */
    public boolean isLoggedIn(){
        return this.loggedIn;
    }

    /**
     * Sends all data to the server. Among other things the position or conditions of the game.
     *
     * @param message data to be transmitted to the server
     */
    public void sendData(String message){

        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Receives the data from the server that was sent to the client via socket connection.
     *
     * @return the raw message received from the server
     */
    public String receiveData(){
        String returnString;
        try {
            returnString = input.readUTF();
            return returnString;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

}
