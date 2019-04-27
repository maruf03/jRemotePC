import java.net.Socket;
import java.awt.Robot;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.OutputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.lang.Thread;
import java.lang.Runnable;

public class ShareScreen implements Runnable {
    private Socket socket = null;
    private Robot robot = null;
    private Rectangle rectangle = null;

    OutputStream out = null;

    public ShareScreen() {
        this.socket = socket;
        this.robot = robot;
        this.rectangle = rectangle;
    }

    @Override
    public void run() {
        try {
            out = this.socket.getOutputStream();
        }
        catch(IOException e){
            System.out.println(e);
        }

        while(true) {
            BufferedImage image = this.robot.createScreenCapture(this.rectangle);

            try {
                ImageIO.write(image, "jpeg", out);
            }
            catch(IOException e) {
                System.out.println(e);
            }

            try {
                Thread.sleep(5);
            }
            catch(InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}
