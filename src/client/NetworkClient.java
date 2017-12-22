package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class NetworkClient{

    private DataInputStream input;
    private DataOutputStream output;
    private GamePlay gamePlay;
    private boolean loggedIn = false;
    private String id;

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
            //System.out.println(returnString);
            //System.out.println("----------------------------------------");
            return returnString;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

}
