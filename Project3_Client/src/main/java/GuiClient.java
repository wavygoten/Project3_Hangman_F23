
import javafx.application.Platform;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiClient extends Application {

	/* Private data members we need */
	private int port;
	private String category, guessWord, sampleText;
	/* Java FX Components */
	MenuItem exit, re, htp;
	MenuBar mb;
	Menu options;
	TextField portText, guessText;
	Label gnLabel, glLabel, cLabel, wnLabel, lcLabel, shortPlay;
	// TextArea message;
	ListView<String> messageArea;
	Button loginBtn, guessBtn, categoryBtn;
	ChoiceBox<String> categoryBox;
	Scene startScene;
	Client clientConnection;

	/* Default constructor */
	public GuiClient() {
		// Init data members
		sampleText = "";
		// Init Javafx Components

		/* Login Scene */
		loginBtn = new Button("Login");
		categoryBox = new ChoiceBox<>();
		categoryBox.getItems().addAll("Thanksgiving", "Food", "US States");
		portText = new TextField();
		portText.setPromptText("Enter port number");
		/* End of Login Scene */

		/* Game Scene */
		shortPlay = new Label(
				"Please guess a letter or word in the box and submit to see if it exists or is correct. The game will end if you run out of guesses or guess the correct word.");

		exit = new MenuItem("Exit");
		re = new MenuItem("Fresh Start");
		htp = new MenuItem("How to Play");
		mb = new MenuBar();
		options = new Menu("Options");
		mb.getMenus().add(options);
		options.getItems().addAll(exit, re, htp);

		guessText = new TextField();
		guessText.setPromptText("Guess word or letter here");
		guessBtn = new Button("Guess");

		// message = new TextArea();
		messageArea = new ListView<>();

		/* End of Game Scene */

		/* Category Scene */
		categoryBtn = new Button("Choose New Category");

		/* End of Category Scene */

		/* Add css classes */
		// loginBtn.getStyleClass().add("loginbtn");
		
		/* End of css classes */
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Client Login Page");

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

		re.setOnAction(e -> restartButton(e, primaryStage));
		exit.setOnAction(e -> exitButton(e));
		htp.setOnAction(e -> htpButton(e));

		loginBtn.setOnAction(event -> {
			try {
				port = Integer.valueOf(portText.getText());
				category = categoryBox.getSelectionModel().getSelectedItem();
				System.out.println("Port: " + port + "\nCategory: " + category);
				Scene gameScene = gameScene();
				gameScene.getStylesheets().add("style.css");
				primaryStage.setScene(gameScene);
				primaryStage.setTitle("Hangman Game Client");
				clientConnection = new Client(data -> {
					Platform.runLater(() -> {
						// message.appendText(clientConnection.message);
						if (clientConnection.message.equals("client won and got string right")
								|| clientConnection.message.equals("ran out of category")) {
							// go to category scene
							sampleText = "";

							// message.clear();
							messageArea.getItems().clear();
							Scene catScene = categoryScene();
							catScene.getStylesheets().add("style.css");
							primaryStage.setScene(catScene);
							primaryStage.setTitle("Hangman Category Scene - Client");
						} else if (clientConnection.gm.getWordGuessCount() == 0
								|| clientConnection.gm.getGuessCount() == 0) {
							// reset the counts

							// dont pop the
							sampleText = "";
							// message.clear();
							messageArea.getItems().clear();
							Scene catScene = categorySceneNoPop();
							catScene.getStylesheets().add("style.css");
							primaryStage.setScene(catScene);
							primaryStage.setTitle("Hangman Category Scene - Client");
						} else {
							if (sampleText.length() == 0) {
								for (int i = 0; i < clientConnection.gm.getLetterCount(); i++) {
									sampleText += "_";
								}
							}
							for (int i = 0; i < clientConnection.gm.getLetterCount(); i++) {
								if (clientConnection.gm.getPositions().contains(i)) {
									sampleText = sampleText.substring(0, i) + guessWord.charAt(0)
											+ sampleText.substring(i + 1);
								}
							}
							messageArea.getItems().add("Letter " + guessWord + " exists in the word at positions "
									+ clientConnection.gm.getPositions() + " -> " + sampleText);

						}

					});
				}, port, category);
				clientConnection.start();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				exitButton(event);
			}
		});

		guessBtn.setOnAction(event -> {
			try {
				System.out.println(guessText.getText());
				guessWord = guessText.getText();
				clientConnection.sendGuess(guessText.getText()); // testing clint if it works.
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		});

		categoryBtn.setOnAction(event -> {
			try {
				category = categoryBox.getSelectionModel().getSelectedItem();
				// System.out.println(category);
				Scene gameScene = gameScene();
				gameScene.getStylesheets().add("style.css");
				primaryStage.setScene(gameScene);
				primaryStage.setTitle("Hangman Game Client");
				// clientConnection.category = category;
				clientConnection.sendCategory(category);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		});
	}

	public Scene gameScene() {

		HBox gameControls = new HBox(20, guessText, guessBtn);
		VBox root = new VBox(50, mb, shortPlay, gameControls, messageArea);
		return new Scene(root, 700, 700);
	}

	public Scene loginScene() {
		Label whatToDo = new Label("Please enter port number and select category, then press login.");
		VBox root = new VBox(20, portText, categoryBox, loginBtn, whatToDo);
		return new Scene(root, 500, 200);
	}

	public Scene categoryScene() {
		// clientConnection.
		categoryBox.getItems().remove(category);
		VBox root = new VBox(20, mb, categoryBox, categoryBtn);
		return new Scene(root, 400, 200);
	}

	public Scene categorySceneNoPop() {
		VBox root = new VBox(20, mb, categoryBox, categoryBtn);
		return new Scene(root, 400, 200);
	}

	private void restartButton(ActionEvent event, Stage stage) {
		messageArea.getItems().clear();
		categoryBox.getItems().clear();
		categoryBox.getItems().addAll("Thanksgiving", "Food", "US States");
		try {
			start(stage);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void exitButton(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}

	private void htpButton(ActionEvent event) {
		String howString = "To play this game\n Guess a letter or word in the input box (You have 6 letter guesses and 3 word guesses)";
		messageArea.getItems().add(howString);
	}

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

// public Scene createClientGui() {

// clientBox = new VBox(10, c1, b1, listItems2);
// clientBox.setStyle("-fx-background-color: blue");
// Scene retS = new Scene(clientBox, 500, 400);
// retS.getStylesheets().add("style.css");
// return retS;

// }