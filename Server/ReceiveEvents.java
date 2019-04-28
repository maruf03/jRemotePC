import java.awt.Robot;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.awt.Toolkit;
import java.awt.Dimension;

class ReceiveEvents extends Thread{
	Socket socket= null;
	Robot robot = null;
	boolean continueLoop = true;
	double width;
	double height;

	public ReceiveEvents(Socket socket, Robot robot){
		this.socket = socket;
		this.robot = robot;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = dim.getWidth();
		height = dim.getHeight();
		start();
	}

	@Override
	public void run(){
		Scanner scanner = null;
		try {
			scanner = new Scanner(socket.getInputStream());
			while(continueLoop){
				int command = scanner.nextInt();
				switch(command){
					case-1:
					robot.mousePress(scanner.nextInt());
					break;
					case-2:
					robot.mouseRelease(scanner.nextInt());
					break;
					case-3:
					robot.keyPress(scanner.nextInt());
					break;
					case-4:
					robot.keyRelease(scanner.nextInt());
					break;
					case-5:
					double x = this.width*scanner.nextDouble();
					double y = this.height*scanner.nextDouble();
					robot.mouseMove((int)x, (int)y);
					break;
				}
			}
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}			
}




