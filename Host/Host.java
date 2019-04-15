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

public class Host extends Thread{
    private ServerSocket server = null;
    private Socket socket = null;
    private DataInputStream din = null;
    private DataOutputStream dout = null;
    private Robot robot = null;
    private Rectangle rectangle = null;
    private String width;
    private String height;
    private String pass;
    private String user;

    public Host(int port, String user, String pass){
        try{
            this.server = new ServerSocket(port);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void run(){
        try{
            GraphicsEnvironment Env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = Env.getDefaultScreenDevice();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

            this.width = "" + dim.getWidth();
            this.height = "" + dim.getHeight();

            this.rectangle = new Rectangle(dim);
            this.robot = new Robot(device);

            while(true){
                System.out.println("Server Running");
                socket = server.accept();
                din = new DataInputStream(socket.getInputStream());
                dout = new DataOutputStream(socket.getOutputStream());

                String username = din.readUTF();
                String password = din.readUTF();
            
                if(password.equals(this.pass) && username.equals(this.user)){
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
    public void stopHost(){
        try{
            System.out.println("Stopped Server Thread");
            this.socket.close();
            this.server.close();
            this.din.close();
            this.dout.close();
            this.stop();
        }
        catch(Exception e){

        }
    }
}