import java.lang.Runnable;
import java.net.Socket;
import java.io.*;

public class Client implements Runnable{
    private String hostAddress;
    private int hostPort;

    Client(String address, int port){
        this.hostAddress = address;
        this.hostPort = port;
    }

    @Override
    public void run(){
        
    } 
}