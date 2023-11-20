
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

	private int remainingWordGuesses, category, wordNumber, letterCount, port, remainingLetterGuesses;
	private boolean isLetterInWord, gameResult;
	private char guessLetter;
	/* Java FX Components */
	private MenuItem exit, re, htp;
	TextField portText, guessWord, guessChar;
	Label gnLabel, glLabel, cLabel, wnLabel, lcLabel;
	TextArea message;
	Button loginBtn;
	ChoiceBox<String> categoryBox;
	Scene startScene;
	Client clientConnection;

	/* Default constructor */
	public GuiClient() {
		// Init data members
		gameResult = false;
		remainingLetterGuesses = 6;
		remainingWordGuesses = 3;

		// Init Javafx Components

		/* Login Scene */
		loginBtn = new Button("Login");
		categoryBox = new ChoiceBox<>();
		categoryBox.getItems().addAll("Category 1", "Category 2", "Category 3");
		portText = new TextField();
		portText.setPromptText("Enter port number");
		/* End of Login Scene */

		/* Game Scene */

		exit = new MenuItem("Exit");
		re = new MenuItem("Fresh Start");
		htp = new MenuItem("How to Play");
		message = new TextArea();
		/* End of Game Scene */

		/* Add css classes */

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

		// this.clientChoice = new Button("Client");
		// this.clientChoice.setStyle("-fx-pref-width: 300px");
		// this.clientChoice.setStyle("-fx-pref-height: 300px");

		// this.clientChoice.setOnAction(e -> {
		// primaryStage.setScene(createClientGui());
		// primaryStage.setTitle("This is a client");
		// clientConnection = new Client(data -> {
		// Platform.runLater(() -> {
		// listItems2.getItems().add(data.toString());
		// });
		// });

		// clientConnection.start();
		// });

		startScene = loginScene();
		startScene.getStylesheets().add("style.css");

		// b1.setOnAction(e -> {
		// clientConnection.send(c1.getText());
		// c1.clear();
		// });

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});

		primaryStage.setScene(startScene);
		primaryStage.show();

		re.setOnAction(e -> restartButton(e));
		exit.setOnAction(e -> exitButton(e));
		htp.setOnAction(e -> htpButton(e));

		loginBtn.setOnAction(event -> {
			try {
				port = Integer.valueOf(portText.getText());
				category = categoryBox.getSelectionModel().getSelectedIndex();
				System.out.println("Port: " + port + "\nCategory: " + category);
				Scene gameScene = createGameScene();
				gameScene.getStylesheets().add("style.css");
				primaryStage.setScene(gameScene);
				primaryStage.setTitle("Hangman Game Client");
				clientConnection = new Client(data -> {
					Platform.runLater(() -> {
						// do javafx stuff here?
						// listItems2.getItems().add(data.toString());
					});
				}, port);
				clientConnection.start();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				exitButton(event);
			}

		});
	}

	public Scene createGameScene() {
		MenuBar mb = new MenuBar();
		Menu options = new Menu("Options");
		mb.getMenus().add(options);
		options.getItems().addAll(exit, re, htp);
		VBox root = new VBox(50, mb);
		return new Scene(root, 700, 700);
	}

	public Scene loginScene() {
		Label whatToDo = new Label("Please enter port number and select category, then press login.");
		VBox root = new VBox(20, portText, categoryBox, loginBtn, whatToDo);
		return new Scene(root, 500, 200);
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

	private void restartButton(ActionEvent event) {
		// currentBalance = 100;
		// totalWinnings = 0;
		// bankerHand.clear();
		// playerHand.clear();
		// theDealer.shuffleDeck();
		// roundNumber = 1;
		// intWinnings.setText("$" + String.valueOf(totalWinnings));
		// intBalance.setText("$" + String.valueOf(currentBalance));
		// rN.setText(String.valueOf(roundNumber));
		// message.setText("");
		// intBankerTotal.setText("0");
		// intPlayerTotal.setText("0");
		// bankerBetAmount.setPromptText("$0");
		// bankerBetAmount.setText("");
		// bankerBetAmount.setDisable(true);
		// tieBetAmount.setPromptText("$0");
		// tieBetAmount.setText("");
		// tieBetAmount.setDisable(true);
		// playerBetAmount.setPromptText("$0");
		// playerBetAmount.setText("");
		// playerBetAmount.setDisable(true);
		// playerCardOneView.setImage(null);
		// playerCardTwoView.setImage(null);
		// playerCardThreeView.setImage(null);
		// bankerCardOneView.setImage(null);
		// bankerCardTwoView.setImage(null);
		// bankerCardThreeView.setImage(null);
		// playButton.setDisable(true);
	}

	private void exitButton(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}

	private void htpButton(ActionEvent event) {
		String howString = "To play this game\n1. blahblahblah";
		message.setText(howString);
		clientConnection.send("banana"); // testing clint if it works.
	}

}
