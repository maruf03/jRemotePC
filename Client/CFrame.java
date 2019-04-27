import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.util.zip.*;

import java.io.IOException;

class CFrame extends Thread {
	String width="", height="";
	private JFrame frame = new JFrame();

	
	private JPanel cPanel = new JPanel();

	public CFrame(Socket cSocket, String width, String height) {

		this.width = width;
		this.height = height;
		this.cSocket = cSocket;
		start();
	}
	
	//Draw GUI per each connected client

	public void drawGUI() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Show thr frame in maximized state
	
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);		//CHECK THIS LINE
		frame.setVisible(true);
        frame.add(cPanel);
        frame.pack();
        cPanel.setFocusable(true);		
	}

	public void run() { 
		//Used to read screenshots
		InputStream in = null;
		//start drawing GUI
		drawGUI();

		try{
			in = cSocket.getInputStream();
			}catch (IOException ex){
			ex.printStackTrace();
		}

		//Start receiving screenshots
		new ReceiveScreen(in,cPanel);
		//Start sending events to the client
		new SendEvents(cSocket,cPanel,width,height);
	}
}
