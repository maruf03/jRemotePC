import java.awt.AWTException;
import java.awt.Robot;
import java.net.Socket;
import java.util.Scanner;

public class EventManager extends Thread {
    private Socket socket = null;
    private Robot robot = null;
    private Scanner scanner = null;

    public EventManager(Socket socket) {
        this.socket = socket;
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        this.start();
    }

    @Override
    public void run(){
        try {
            this.scanner = new Scanner(socket.getInputStream());
            while(true){
                int command = scanner.nextInt();
                switch(command){
                    case -1:
                        robot.mousePress(scanner.nextInt());
                        break;
                    case -2:
                        robot.mouseRelease(scanner.nextInt());
                        break;
                    case -3:
                        robot.mouseMove(scanner.nextInt(), scanner.nextInt());
                        break;
                    case -4:
        
                        break;
                    case -5:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}