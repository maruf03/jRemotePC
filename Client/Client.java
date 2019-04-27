import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.lang.Thread;
import java.net.UnknownHostException;

public class Client extends Thread{
	Socket socket = null;
	DataInputStream inputStream = null;
    DataOutputStream outputStream = null;

    String verify = "";
	String width="";
    String height="";
    
    String username;
    String password;
    String ip;
    int port;
			
	public Client(int port, String ip, String username, String password){
        this.port = port;
        this.ip = ip;
        this.username = username;
        this.password = password; 
        try {
            this.socket = new Socket(this.ip, this.port);
            this.inputStream = new DataInputStream(this.socket.getInputStream());
            this.outputStream = new DataOutputStream(this.socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        try {
            outputStream.writeUTF(this.username);
            outputStream.writeUTF(this.password);
            this.verify = inputStream.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(verify.equals("valid")){
            try {
                this.width = inputStream.readUTF();
                this.height = inputStream.readUTF();
                CFrame frame = new CFrame(this.socket, this.width, this.height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Invalid Credentials");
            System.exit(0);
        }
    }
}
