import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiServer extends Application {
	private int port;
	TextField s1, s2, s3, s4, c1, portText;
	Button serverChoice, clientChoice, b1, loginBtn;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	Server serverConnection;
	ListView<String> listItems, listItems2;

	public GuiServer() {
		portText = new TextField();
		portText.setPromptText("Enter port number");
		loginBtn = new Button("Login");
		listItems = new ListView<String>();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Server Login Scene");

		loginBtn.setOnAction(event -> {
			try {
				port = Integer.valueOf(portText.getText());
				primaryStage.setScene(createServerGui());
				primaryStage.setTitle("This is the Server");
				serverConnection = new Server(data -> {
					Platform.runLater(() -> {
						listItems.getItems().add(data.toString());
					});
				}, port);
			} catch (Exception e) {
				e.printStackTrace();
				Platform.exit();
				System.exit(0);
				// TODO: handle exception
			}
		});

		startScene = loginScene();
		startScene.getStylesheets().add("style.css");

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});

		primaryStage.setScene(startScene);
		primaryStage.show();

	}

	public Scene createServerGui() {

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: coral");
		pane.setCenter(listItems);
		Scene retS = new Scene(pane, 500, 400);
		retS.getStylesheets().add("style.css");
		return retS;
	}

	public Scene loginScene() {
		Label whatToDo = new Label("Please enter port number, then press login.");
		VBox root = new VBox(20, portText, loginBtn, whatToDo);
		return new Scene(root, 500, 200);
	}

}
