import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JPanel;

/**
 * SendEvent implements AWT Event listeners and captures all event occurred in the JPanel of CFrame and sends 
 * the event data to the server
 */

class SendEvents implements KeyListener, MouseMotionListener, MouseListener{
	private Socket socket = null;
	private JPanel Panel = null;
	private PrintWriter writer = null;
	private String width = "";
	private String height = "";
	private double w;
	private double h;

	SendEvents(Socket s, JPanel p, String width, String height){
		socket = s;
		Panel = p;
		this.width = width;
		this.height = height;
		w = Double.valueOf(width.trim()).doubleValue();
		h = Double.valueOf(height.trim()).doubleValue();

		//Associate event listeners to the panel

		Panel.addKeyListener(this);
		Panel.addMouseListener(this);
		Panel.addMouseMotionListener(this);

		try{
			//Prepare PrintWriter which will be used to send events to the client
			writer = new PrintWriter(socket.getOutputStream());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void mouseDragged(MouseEvent e){
	}

	public void mouseMoved(MouseEvent e){
		//Scaling factor of the events on client side to map to server side screen
		double xScale = (double)w/cPanel.getWidth();
		double yScale = (double)h/cPanel.getHeight();
		writer.println(Commands.MOVE_MOUSE);
		writer.println((int)(e.getX()*xScale));
		writer.println((int)(e.getY()*yScale));
		writer.flush();
	}

	public void mouseClicked(MouseEvent e){
	}

	public void mousePressed(MouseEvent e){
		writer.println(Commands.PRESS_MOUSE);
		int button = e.getButton();
		int xButton = 16;
		if(button==3){
			xButton = 4;
		}
		writer.println(xButton);
		writer.flush();
	}

	public void mouseReleased(MouseEvent e){
		writer.println(Commands.RELEASE_MOUSE);
		int button = e.getButton();
		int xButton = 16;
		if(button==3){
			xButton = 4;
		}
		writer.println(xButton);
		writer.flush();
	}

	public void mouseEntered(MouseEvent e){
	}

	public void mouseExited(MouseEvent e){
	}

	public void keyTyped(KeyEvent e){
	}

	public void keyPressed(KeyEvent e){
		writer.println(Commands.PRESS_KEY);
		writer.println(e.getKeyCode());
		writer.flush();
	}

	public void keyReleased(KeyEvent e){
		writer.println(Commands.RELEASE_KEY);
		writer.println(e.getKeyCode());
		writer.flush();
	}
}
