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

public class ClientGUI extends Application{
    private HBox hbox = null;
    private VBox vbox = null;

    private Label labelUsername = null;
    private Label labelPassword = null;
    private Label labelAddress = null;
    private Label labelPort = null;

    private TextField usernameField = null;
    private PasswordField passwordField = null;
    private TextField addressField = null;
    private TextField portField = null;

    private Button connectButton = null;

    private Scene mainScene = null;

    @Override
    public void start(Stage primaryStage){
        labelUsername = new Label("Username:");
        labelPassword = new Label("Password:");
        labelPort = new Label("Port:");
        labelAddress = new Label("IP:");

        usernameField = new TextField();
        passwordField = new PasswordField();
        portField = new TextField();
        addressField = new TextField();

        connectButton = new Button("Connect");
        connectButton.setOnAction(this::onConnectButton);
        hbox = new HBox(connectButton);
        hbox.setAlignment(Pos.CENTER);
        vbox = new VBox(labelUsername, usernameField, labelPassword, passwordField, labelAddress, addressField, labelPort, portField, hbox);

        mainScene = new Scene(vbox);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Client Settings");
        primaryStage.show();

    }

    private void onConnectButton(ActionEvent event){
        try {
            try {
                int port = Integer.parseInt(portField.getText());
                String ip = addressField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                try {
                    Client c = new Client(port, ip, username, password);
                    c.start();
                } catch (Exception e) {
                }
            } catch (Exception e) {
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