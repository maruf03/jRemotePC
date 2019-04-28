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
	Socket cSocket = null;
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
		frame.add(cPanel);
		cPanel.setFocusable(true);
		cPanel.requestFocusInWindow();
		frame.pack();
		frame.setVisible(true);
        		
	}

	public void run() { 
		InputStream in = null;
		drawGUI();

		try{
			in = cSocket.getInputStream();
			}catch (IOException e){
			e.printStackTrace();
		}
		//Start receiving screenshots
		new ReceiveScreen(in,cPanel);
		//Start sending events to the client
		new SendEvents(cSocket,cPanel,width,height);
	}
}
