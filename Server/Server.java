import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
		
	ServerSocket socket = null;
	DataInputStream inputStream = null;
	DataOutputStream outputStream = null;
	String width="";
	String height="";
			
	Server(int port,String username, String password){
		Robot robot = null;
		Rectangle rectangle = null;
		try{
			System.out.println("Awaiting Connection from Client");
			socket=new ServerSocket(port);
			
			GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
	
			Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
			String width=""+dim.getWidth();
			String height=""+dim.getHeight();
			rectangle=new Rectangle(dim);
			robot=new Robot(gDev);

			while(true){
				Socket sc=socket.accept();
				inputStream = new DataInputStream(sc.getInputStream());
				outputStream = new DataOutputStream(sc.getOutputStream());
				String user = inputStream.readUTF();
				String pass = inputStream.readUTF();
				
				if(username.equals(user) && password.equals(pass)){
					outputStream.writeUTF("valid");
					outputStream.writeUTF(width);
					outputStream.writeUTF(height);
					new SendScreen(sc,robot,rectangle);
					new ReceiveEvents(sc,robot);}
				else{
					outputStream.writeUTF("invalid");
				}
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
