import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class Client extends Thread {

	Socket socketClient;

	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	GameMessage gm;

	private Consumer<Serializable> callback;
	private int port;
	String category, userC, userG;

	Client(Consumer<Serializable> call, int p, String c) {
		port = p;
		callback = call;
		category = c;
		message = userC = userG = "";

	}

	Client(Consumer<Serializable> call, String c) {
		callback = call;
		category = c;
		message = userC = userG = "";
	}

	public void run() {

		try {
			socketClient = new Socket("127.0.0.1", port);
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		sendCategory(category);

		while (true) {
			try {
				byte b = in.readByte();
				if (b == 1) {
					GameMessage msg = (GameMessage) in.readObject();
					gm = msg;
					if (msg.getPositions() == null && msg.getGuessCount() == -10 && msg.isInWord() == false) {
						// we won
						System.out.println("client won and got string right");
						message = "client won and got string right";
					} else {
						// something else happened
						System.out.println(msg.getPositions().toString() + msg.getGuessCount() + msg.isInWord()
								+ msg.getGuessCount() + msg.getWordGuessCount());
						message = msg.getPositions().toString() + msg.getGuessCount() + msg.isInWord()
								+ msg.getLetterCount() + msg.getGuessCount() + msg.getWordGuessCount();
					}
					callback.accept(msg);
				} else if (b == 2) {

					// message = in.readObject().toString();
					// callback.accept(message);
				} else {

				}

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

	}

	/**
	 * This sends the guess to the server
	 * 
	 * @param data
	 */
	public void sendGuess(String data) {
		try {
			userG = data;
			out.writeByte(1); // Identifier for String
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This sends the category to the server
	 * 
	 * @param data
	 */
	public void sendCategory(String data) {
		try {
			userC = data;
			out.writeByte(2); // Identifier for int
			out.writeObject(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setUserC(String userC) {
		this.userC = userC;
	}

	public void setUserG(String userG) {
		this.userG = userG;
	}
}
