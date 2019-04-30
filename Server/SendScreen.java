import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;

/**
 * SendScreen creates a new Thread and sends the screenshot in every 10 milliseconds to the client
 */
class SendScreen extends Thread{

	private Socket socket = null;
	private Robot robot = null;
	private Rectangle rectangle = null;
	
	private OutputStream out = null;

	public SendScreen(Socket socket,Robot robot,Rectangle rect) {
		this.socket=socket;
		this.robot=robot;
		rectangle=rect;
		start();
	}

	public void run(){
		try{
			oos=socket.getOutputStream();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		while(true){
			//Taking the screen shot of the host machine
			BufferedImage image=robot.createScreenCapture(rectangle);

			try{
				//Sending the screenshot image to socket output stream
				ImageIO.write(image,"jpeg",out);
			}catch(IOException ex){
				ex.printStackTrace();
			}
			
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}

