import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;


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
			BufferedImage image=robot.createScreenCapture(rectangle);

			try{
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

