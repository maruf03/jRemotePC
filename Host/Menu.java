import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu extends Application {
    private Config config = null;

    private VBox vbox = null;
    private HBox hbox = null;

    private Label labelUserName = null;
    private Label labelPassword = null;

    private TextField userField = null;
    private PasswordField passwordField = null;

    private Button saveButton = null;
    private Button startButton = null;

    private Scene mainScene = null;

    @Override
    public void start(Stage primaryStage) {
        this.labelUserName = new Label("Username:");
        this.labelPassword = new Label("Password:");
        this.userField = new TextField();
        this.passwordField = new PasswordField();
        //this.passwordField.setText();
        this.vbox = new VBox(this.labelUserName, this.userField, this.labelPassword, this.passwordField);
        this.saveButton = new Button("Save");
        this.startButton = new Button("Start");
        this.hbox = new HBox(this.saveButton, this.startButton);
        this.hbox.setSpacing(15);
        this.vbox.getChildren().add(this.hbox);
        this.vbox.setSpacing(5);
        this.mainScene = new Scene(this.vbox, 200, 150);
        primaryStage.setScene(this.mainScene);
        primaryStage.setTitle("Start Host");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}