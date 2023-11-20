
import java.util.HashMap;

import javafx.application.Platform;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiClient extends Application {

	TextField s1, s2, s3, s4, c1;
	Button serverChoice, clientChoice, b1;
	// HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	// Server serverConnection;
	Client clientConnection;

	ListView<String> listItems, listItems2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("The Networked Client/Server GUI Example");

		// this.serverChoice = new Button("Server");
		// this.serverChoice.setStyle("-fx-pref-width: 300px");
		// this.serverChoice.setStyle("-fx-pref-height: 300px");

		// this.serverChoice.setOnAction(e -> {
		// primaryStage.setScene(createServerGui());
		// primaryStage.setTitle("This is the Server");
		// serverConnection = new Server(data -> {
		// Platform.runLater(() -> {
		// listItems.getItems().add(data.toString());
		// });

		// });

		// });

		this.clientChoice = new Button("Client");
		this.clientChoice.setStyle("-fx-pref-width: 300px");
		this.clientChoice.setStyle("-fx-pref-height: 300px");

		this.clientChoice.setOnAction(e -> {
			primaryStage.setScene(createClientGui());
			primaryStage.setTitle("This is a client");
			clientConnection = new Client(data -> {
				Platform.runLater(() -> {
					listItems2.getItems().add(data.toString());
				});
			});

			clientConnection.start();
		});

		// this.buttonBox = new HBox(400, serverChoice, clientChoice);

		this.buttonBox = new HBox(400, clientChoice);
		startPane = new BorderPane();
		startPane.setPadding(new Insets(70));
		startPane.setCenter(buttonBox);
		startScene = new Scene(startPane, 800, 800);
		startScene.getStylesheets().add("style.css");

		listItems = new ListView<String>();
		listItems2 = new ListView<String>();

		c1 = new TextField();
		b1 = new Button("Send");
		b1.setOnAction(e -> {
			clientConnection.send(c1.getText());
			c1.clear();
		});

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

	// public Scene createServerGui() {

	// BorderPane pane = new BorderPane();
	// pane.setPadding(new Insets(70));
	// pane.setStyle("-fx-background-color: coral");
	// //
	// pane.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
	// pane.setCenter(listItems);
	// Scene retS = new Scene(pane, 500, 400);
	// retS.getStylesheets().add("style.css");
	// return retS;
	// }

	public Scene createClientGui() {

		clientBox = new VBox(10, c1, b1, listItems2);
		clientBox.setStyle("-fx-background-color: blue");
		Scene retS = new Scene(clientBox, 500, 400);
		retS.getStylesheets().add("style.css");
		return retS;

	}

}
