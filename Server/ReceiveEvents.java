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
					robot.mouseMove((int)this.width*scanner.nextInt(), (int)this.height*scanner.nextInt());
					break;
				}
			}
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}			
}




