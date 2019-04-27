import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.Robot;
import java.awt.Rectangle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {
    private Config config = null;
    private File file = null;

    private VBox vbox = null;
    private HBox hbox = null;

    private Label labelUserName = null;
    private Label labelPassword = null;
    private Label labelPort = null;

    private TextField userField = null;
    private PasswordField passwordField = null;
    private TextField portField = null;

    private Button saveButton = null;
    private Button startButton = null;
    private Button stopButton = null;
    private Button loadButton = null;

    private Scene mainScene = null;

    //private EventManager eventManager = null;
    private ServerSocket server = null;
    private int port = 6000; //Default port is 6000
    private Socket socket = null;
    private Robot robot = null;
    private Rectangle rectangle = null;

    @Override
    public void start(Stage primaryStage) {
        this.file = new File("settings.config");
        this.labelUserName = new Label("Username:");
        this.labelPassword = new Label("Password:");
        this.labelPort = new Label("Port:");
        this.userField = new TextField();
        this.passwordField = new PasswordField();
        this.portField = new TextField();
        
        this.vbox = new VBox(this.labelUserName, this.userField, this.labelPassword, this.passwordField, this.labelPort, this.portField);
        this.saveButton = new Button("Save");
        this.saveButton.setOnAction(this::onSaveButton);
        this.startButton = new Button("Start");
        this.startButton.setOnAction(this::onStartButton);
        this.loadButton = new Button("Load");
        this.loadButton.setOnAction(this::onLoadButton);
        this.stopButton = new Button("Stop");
        this.stopButton.setOnAction(this::onStopButton);
        this.stopButton.setDisable(true);
        this.hbox = new HBox(this.saveButton, this.loadButton, this.startButton, this.stopButton);
        this.hbox.setSpacing(15);
        this.vbox.getChildren().add(this.hbox);
        this.vbox.setSpacing(5);
        this.mainScene = new Scene(this.vbox, 200, 200);
        primaryStage.setScene(this.mainScene);
        primaryStage.setTitle("Start Host");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    private void onSaveButton(ActionEvent event){
        if(this.file.exists() && this.userField.getText().length() != 0 && this.passwordField.getText().length() != 0){
            this.file.delete();
        }
        try{
            this.file.createNewFile();
            this.config = new Config(this.file);
            if(this.userField.getText().length() != 0 && this.passwordField.getText().length() != 0){
                this.config.saveSettings(this.userField.getText(), this.passwordField.getText());
            }
            else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid settings");
                alert.setContentText("Username or Password Field cannot be empty");
                alert.showAndWait();
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    private void onStartButton(ActionEvent event){
        try{
            if(this.userField.getText().length() != 0 && this.passwordField.getText().length() != 0){
                this.port = Integer.parseInt(this.portField.getText());
                this.server = new ServerSocket(this.port);
                Platform.runLater(new Runnable(){
                
                    @Override
                    public void run() {
                        while (true) {
                            Socket sc = null;
                            try {
                                sc = server.accept();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }   
                            
                        }
                    }
                });
                this.startButton.setDisable(true);
                this.stopButton.setDisable(false);
            }
            else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid settings");
                alert.setContentText("Username or Password Field cannot be empty");
                alert.showAndWait();
            }
        }
        catch(Exception e){
            System.out.println("I came Here");
            System.out.println(e);
        }
    }
    private void onLoadButton(ActionEvent event){
        try {
            if(this.file.exists()){
                this.config = new Config(this.file);
                this.config.getSettingsFromFile();
                this.userField.setText(this.config.getUserName());
                this.passwordField.setText(this.config.getPassword());
            }
            else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No settings found");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void onStopButton(ActionEvent event){
        try{
            this.startButton.setDisable(false);
            this.stopButton.setDisable(true);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}