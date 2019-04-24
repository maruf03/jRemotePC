import java.lang.Runnable;
import java.net.Socket;
import java.io.*;
import javafx.scene.image.ImageView;

public class Client implements Runnable{
    private Socket socket = null;
    private ImageView image = null;

    Client(Socket socket, ImageView image){
        this.socket = socket;
        
    }

    @Override
    public void run(){
        
    } 
}