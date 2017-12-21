package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class NetworkClient{

    private Socket socket;

    private DataInputStream input;
    private DataOutputStream output;

    private String id;

    public NetworkClient() {

        try{
            this.socket=new Socket("localhost", 3337);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            id=new Login().getLoginID();
            System.out.println("Player --->"+id);
            this.sendData(id);

        }catch(IOException e){
            e.printStackTrace();
        }
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
            returnString=input.readUTF();
            System.out.println(returnString);
            System.out.println("----------------------------------------");
            return returnString;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

}
