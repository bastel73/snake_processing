package server;

import java.io.IOException;
import java.net.*;

public class GameServer extends Thread {


    private DatagramSocket socket;
    private Game game;

    public GameServer() {

        try {
            this.socket = new DatagramSocket(3333);

        } catch (SocketException e) {
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
            String message = new String(packet.getData());
            System.out.println("Client > " + new String(message));
            if(message.trim().equalsIgnoreCase("ping")) {

                sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
            }

        }
    }

    public void sendData (byte[] data, InetAddress ipAddress, int port){
        DatagramPacket packet=new DatagramPacket(data, data.length, ipAddress, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
