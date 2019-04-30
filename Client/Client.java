import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.lang.Thread;
import java.net.UnknownHostException;
/**
 * Creates a connection with the server and calls CFrame to manage the rest of the system
 */
public class Client extends Thread{
	private Socket socket = null;
	private DataInputStream inputStream = null;
    private DataOutputStream outputStream = null;

    private String verify = "";
	private String width="";
    private String height="";
    
    private String username;
    private String password;
    private String ip;
    private int port;
			
	public Client(int port, String ip, String username, String password){
        this.port = port;
        this.ip = ip;
        this.username = username;
        this.password = password; 
        try {
            //connecting with the server 
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
                //CFrame handles event and receives screenshots
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
