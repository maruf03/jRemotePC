import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.geometry.Pos;

public class ServerGUI extends Application{
    private HBox hbox = null;
    private VBox vbox = null;

    private Label labelUsername = null;
    private Label labelPassword = null;
    private Label labelPort = null;

    private TextField usernameField = null;
    private PasswordField passwordField = null;
    private TextField portField = null;

    private Button startButton = null;

    private Scene mainScene = null;

    @Override
    public void start(Stage primaryStage){
        labelUsername = new Label("Username:");
        labelPassword = new Label("Password:");
        labelPort = new Label("Port:");

        usernameField = new TextField();
        passwordField = new PasswordField();
        portField = new TextField();

        startButton = new Button("Start");
        startButton.setOnAction(this::onStartButton);
        hbox = new HBox(startButton);
        hbox.setAlignment(Pos.CENTER);
        vbox = new VBox(labelUsername, usernameField, labelPassword, passwordField, labelPort, portField, hbox);

        mainScene = new Scene(vbox);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Server Settings");
        primaryStage.show();

    }

    private void onStartButton(ActionEvent event){
        try {
            String user = usernameField.getText();
            String pass = passwordField.getText();
            int port = Integer.parseInt(portField.getText());
            System.out.println("Username: " + user);
            System.out.println("Password: " + pass);
            System.out.println("Port: " + port);
            try{
                Thread t = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        new Server(port, user, pass);
                    }
                });
                t.start();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}