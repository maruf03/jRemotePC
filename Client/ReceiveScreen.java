import java.awt.Graphics;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class ReceiveScreen extends Thread{
	private JPanel Panel = null;
	private InputStream input = null;
	private Image image1 = null;

	public ReceiveScreen(InputStream in,JPanel p){
		input = in;
		Panel = p;
		start();
	}

	public void run(){
		try{
			//Read screenshots of the client and then draw them
			while(true){
				byte[] bytes = new byte[1024*1024];
				int count = 0;
				do{
					count += input.read(bytes,count,bytes.length-count);
				}while(!(count>4 && bytes[count-2]==(byte)-1 && bytes[count-1]==(byte)-39));//Reading bytes untill eof of jpeg format is found

				image1 = ImageIO.read(new ByteArrayInputStream(bytes));
				image1 = image1.getScaledInstance(Panel.getWidth(),Panel.getHeight(),Image.SCALE_FAST);

				//Draw the received screenshots

				Graphics graphics = Panel.getGraphics();
				graphics.drawImage(image1, 0, 0, Panel.getWidth(), Panel.getHeight(), Panel);
			}

		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
