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

/**
 * Server opens a ServerSocket and authenticates a client then manages SendScreen and ReceiveEvents thread
*/
public class Server{
		
	private ServerSocket server = null;
	private DataInputStream inputStream = null;
	private DataOutputStream outputStream = null;
			
	Server(int port,String username, String password){
		Robot robot = null;
		Rectangle rectangle = null;
		try{
			System.out.println("Waiting for Connection:");
			server = new ServerSocket(port);
			
			GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
	
			Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
			String width = "" + dim.getWidth();
			String height = "" + dim.getHeight();
			rectangle = new Rectangle(dim);
			robot = new Robot(gDev);

			while(true){
				Socket socket= server.accept();
				inputStream = new DataInputStream(socket.getInputStream());
				outputStream = new DataOutputStream(socket.getOutputStream());
				String user = inputStream.readUTF();
				String pass = inputStream.readUTF();
				
				if(username.equals(user) && password.equals(pass)){
					outputStream.writeUTF("valid");
					outputStream.writeUTF(width);
					outputStream.writeUTF(height);
					//Sends screebshots to the client socket
					new SendScreen(socket,robot,rectangle);
					//Receives events from client socket and disoatch events
					new ReceiveEvents(socket,robot);
				}
				else{
					outputStream.writeUTF("invalid");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
