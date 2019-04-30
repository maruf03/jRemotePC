import java.awt.Robot;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.awt.Toolkit;
import java.awt.Dimension;
/**
 * ReceiveEvents creates a new Thread and receive the events sent from client and dispatch it accordingly
 */
class ReceiveEvents extends Thread{
	private Socket socket= null;
	private Robot robot = null;

	public ReceiveEvents(Socket socket, Robot robot){
		this.socket = socket;
		this.robot = robot;
		start();
	}

	@Override
	public void run(){
		Scanner scanner = null;
		try {
			scanner = new Scanner(socket.getInputStream());
			while(true){
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
					robot.mouseMove(scanner.nextInt(), scanner.nextInt());
					break;
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}			
}




