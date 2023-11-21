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
	String category;

	Client(Consumer<Serializable> call, int p, String c) {
		port = p;
		callback = call;
		category = c;
		message = "";
	}

	Client(Consumer<Serializable> call, String c) {
		callback = call;
		category = c;
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
					// we ran out of words
					message = in.readObject().toString();
					callback.accept(message);
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
			out.writeByte(1); // Identifier for String
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendCategory(String data) {
		try {
			out.writeByte(2); // Identifier for int
			out.writeObject(data);
			// if (category == 0) {
			// out.writeObject("thanksgiving");
			// } else if (category == 1) {
			// out.writeObject("food");
			// } else {
			// out.writeObject("US states");
			// }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
