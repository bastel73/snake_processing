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

    public void hasLoggedIn(String loginName){
        this.loggedIn = true;
        System.out.println("Player --->"+ loginName);
        this.sendData(loginName);
    }

    public boolean isLoggedIn(){
        return this.loggedIn;
    }

    public void sendData(String message){

        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
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
