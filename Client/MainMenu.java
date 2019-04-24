import java.io.File;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

public class MainMenu extends Application implements EventSender{
    private Scene mainScene = null;
    private Scene loginScene = null;

    private Config config = null;
    private File file = null;

    private VBox vbox = null;
    private HBox hbox = null;
    private Pane pane = null;

    private Label labelUserName = null;
    private Label labelPassword = null;
    private Label labelIP = null;
    private Label labelPort = null;

    private TextField userField = null;
    private PasswordField passwordField = null;
    private TextField ipField = null;
    private TextField portField = null;

    private ImageView image = null;
    //private Button saveButton = null;
    private Button startButton = null;
    //private Button loadButton = null;

    private int port = 6000; //Default port is 6000
    private String userName = "admin";
    private String password = "admin";
    private String ip = "192.168.0.1";

    private Socket socket = null;

    private DataInputStream din = null;
    private DataOutputStream dout = null;

    private int width;
    private int height;

    @Override
    public void start(Stage primaryStage) {

        this.labelUserName = new Label("Username:");
        this.labelPassword = new Label("Password:");
        this.labelIP = new Label("IP:");
        this.labelPort = new Label("Port:");

        this.userField = new TextField();
        this.passwordField = new PasswordField();
        this.ipField = new TextField();
        this.portField = new TextField();

        this.startButton = new Button("Connect");
        this.startButton.setOnAction(this::onStartButton);

        this.vbox = new VBox(this.labelUserName, this.userField, this.labelPassword, this.passwordField, this.labelIP, this.ipField, this.labelPort, this.portField);
        this.hbox = new HBox(this.startButton);
        this.hbox.setAlignment(Pos.CENTER);

        this.vbox.getChildren().add(this.hbox);

        this.loginScene = new Scene(this.vbox, 200, 240);
        primaryStage.setScene(this.loginScene);
        primaryStage.setTitle("Connect");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    private void onStartButton(ActionEvent event){
        try{
            this.port = Integer.parseInt(this.portField.getText());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try {
            this.userName = this.userField.getText();
            this.password = this.passwordField.getText();
            this.ip = this.ipField.getText();

            try {
                try{
                    this.socket = new Socket(this.ip, this.port);
                    this.din = new DataInputStream(this.socket.getInputStream());
                    this.dout = new DataOutputStream(this.socket.getOutputStream());

                    this.dout.writeUTF(this.userName);
                    this.dout.writeUTF(this.password);

                    String response = this.din.readUTF();
                    if(response.equals("success")){
                        this.width = Integer.parseInt(this.din.readUTF());
                        this.height = Integer.parseInt(this.din.readUTF());

                        Stage prevstage = (Stage)((Node) event.getSource()).getScene().getWindow();
                        prevstage.close();

                        this.image = new ImageView();
                        Platform.runLater(new Runnable() {
                            @Override 
                            public void run() {
                                BufferedImage bimg = null;
                                Image img = null;
                                while(true){
                                    bimg = ImageIO.read(this.socket.getInputStream());
                                    img = SwingFXUtils.toFXImage(bimg, null);   
                                    this.image.setImage(img);
                                }   
                            }
                        });
                        this.image.setOnKeyPressed(this::keyPressed);
                        this.image.setOnKeyReleased(this::keyReleased);
                        this.image.setOnMousePressed(this::mousePressed);
                        this.image.setOnMouseReleased(this::mouseReleased);
                        this.image.setOnMouseMoved(this::mouseMoved);
                        this.image.setFocusTraversable(true);
                        this.pane = new Pane(this.image);
                        this.mainScene = new Scene(this.pane, this.width, this.height);
                        Stage secondStage = new Stage();
                        secondStage.setScene(this.mainScene);
                        secondStage.setTitle("Remote Desktop");
                        secondStage.show();
                    }
                    else{
                        //Show Invalid login info
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mouseMoved(MouseEvent event){
        System.out.println(event.getSceneX() + ", " + event.getSceneY());
    }
    public void mousePressed(MouseEvent event){
        System.out.println("Mouse Pressed at: " + event.getSceneX() + ", " + event.getSceneY());
    }
    public void mouseReleased(MouseEvent event){
        System.out.println("Mouse Released at: " + event.getSceneX() + ", " + event.getSceneY());
    }
    public void keyPressed(KeyEvent event){
        System.out.println("Key Pressed: " + event.getText());
    }
    public void keyReleased(KeyEvent event){
        System.out.println("Key Released: " + event.getText());
    }
    public static void main(String[] args) {
        launch(args);
    }
}