import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Host {
    private ServerSocket server = null;
    private Socket socket = null;
    private DataInputStream din = null;
    private DataOutputStream dout = null;
    private Robot robot = null;
    private Rectangle rectangle = null;
    private String width;
    private String height;
    
    public Host(int port, String user, String pass){
        try{
            server = new ServerSocket(port);
            
            GraphicsEnvironment Env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = Env.getDefaultScreenDevice();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

            this.width = "" + dim.getWidth();
            this.height = "" + dim.getHeight();

            this.rectangle = new Rectangle(dim);
            this.robot = new Robot(device);

            while(true){
                socket = server.accept();
                din = new DataInputStream(socket.getInputStream());
                dout = new DataOutputStream(socket.getOutputStream());

                String username = din.readUTF();
                String password = din.readUTF();
            
                if(password.equals(pass) && username.equals(user)){
                    dout.writeUTF("success");
                    dout.writeUTF(this.width);
                    dout.writeUTF(this.height);
                    new ShareScreen(socket, robot, rectangle);
                    //Event management goes here
                }
                else {
                    dout.writeUTF("unsuccessful");
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}