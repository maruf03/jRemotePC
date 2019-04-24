import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Config {
    private File file = null;
    private FileInputStream fin = null;
    private FileOutputStream fout = null;

    private String userName = "";
    private String password = "";
    private int port = 6000;

    public Config(File file){
        this.file = file;
    }
    public void getSettingsFromFile(){
        try{
            fin = new FileInputStream(this.file);
            Scanner sc = new Scanner(fin);
            this.userName = sc.next();
            this.password = sc.next();
            this.port = Integer.parseInt(sc.next());
            sc.close();
            fin.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    public String getUserName(){
        return this.userName;
    }
    public String getPassword(){
        return this.password;
    }
    public int getPort(){
        return this.port;
    }
    public void setUserName(String user){
        this.userName = user;
    }
    public void setPassword(String pass){
        this.password = pass;
    }
    public void setPort(int port){
        this.port = port;
    }
    public void saveSettings(String user, String pass, int port) throws IOException{
        this.setUserName(user);
        this.setPassword(pass);
        this.setPort(port);
        this.saveSettings();
    }
    public void saveSettings() throws IOException{
        try{
            fout = new FileOutputStream(this.file);
            String out = this.userName + " " + this.password + " " + this.port;
            byte[] b = out.getBytes();
            fout.write(b);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}