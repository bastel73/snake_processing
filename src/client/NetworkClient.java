package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class NetworkClient extends Thread{
    private InetAddress ipAddress;
    private Socket socket;

    private DataInputStream input;
    private DataOutputStream output;

    private String id;

    public NetworkClient() {

        try{
            this.socket=new Socket("localhost", 3333);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            //id=readString("Bitte Namen eingeben: ");
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



    /*private InetAddress ipAddress;
    private DatagramSocket socket;
    private Game game;

    public NetworkClient(String ipAddress){

        try{
            this.socket=new DatagramSocket();
            this.ipAddress=InetAddress.getByName(ipAddress);
        }catch (SocketException e){
            e.printStackTrace();
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
    }

    public void run(){
        while (true){
            byte[] data=new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Server > "+ new String(packet.getData()));
        }
    }

    public void sendData (byte[] data){
        DatagramPacket packet=new DatagramPacket(data, data.length, ipAddress, 3333);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void run() {

    }

    private String readString(String message){
        System.out.println(message);
        try{
            BufferedReader input = new BufferedReader((new InputStreamReader(System.in)));
            return input.readLine();
        }catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    /*public static void main(String[] args) {
        new NetworkClient("localhost");
    }*/

}
