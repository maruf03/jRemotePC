import java.lang.Runnable;
import java.net.Socket;
import java.io.*;

public class EventSender {
    private Socket socket = null;
    public EventSender(Socket socket){
        this.socket = socket;
    }
    public void sendMouseEvent(int x, int y){

    }
    public void sendKeypressEvent(){
        
    }
}