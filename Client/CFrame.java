import java.io.InputStream;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.IOException;

/**
 * This class Receives screen captures from Server and shows in JPanel and generates Mouse and Keyboard events  
 */
class CFrame extends Thread {
	private String width="";
	private String height="";
	private Socket socket = null;
	private JFrame frame = new JFrame();

	
	private JPanel Panel = new JPanel();

	public CFrame(Socket socket, String width, String height) {

		this.width = width;
		this.height = height;
		this.socket = socket;
		start();
	}
	
	//Draw GUI per each connected client

	public void drawGUI() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Show the frame in maximized state
	
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);		//CHECK THIS LINE
		frame.setVisible(true);
		frame.add(Panel);
        Panel.setFocusable(true);
		Panel.requestFocusInWindow();
	}
	//Updates the screen and receives screenshots and sending Events to server
	@Override
	public void run() { 
		InputStream in = null;
		drawGUI();

		try{
			in = socket.getInputStream();
		}catch (IOException e){
			e.printStackTrace();
		}
		//Start receiving screenshots
		new ReceiveScreen(in,Panel);
		//Start sending events to the client
		new SendEvents(socket,Panel,width,height);
	}
}
