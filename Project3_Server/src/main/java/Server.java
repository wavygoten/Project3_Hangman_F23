import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server {

	int count = 1;
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback;
	private int port;

	Server(Consumer<Serializable> call, int p) {

		callback = call;
		server = new TheServer();
		server.start();
		port = p;
	}

	public class TheServer extends Thread {

		public void run() {

			try (ServerSocket mysocket = new ServerSocket(port);) {
				System.out.println("Server is waiting for a client!");

				while (true) {

					ClientThread c = new ClientThread(mysocket.accept(), count);
					callback.accept("client has connected to server: " + "client #" + count);
					clients.add(c);
					c.start();

					count++;

				}
			} // end of try
			catch (Exception e) {
				callback.accept("Server socket did not launch");
			}
		}// end of while
	}

	class ClientThread extends Thread {

		Socket connection;
		int count, letterCount, rand;
		ObjectInputStream in;
		ObjectOutputStream out;
		String word, currCategory;
		boolean won, letterInWord;
		int guessCount, wordGuessCount, wordCounter;
		Categories categories;

		ClientThread(Socket s, int count) {
			this.connection = s;
			this.count = count;
			word = "";
			won = letterInWord = false;
			guessCount = 6;
			wordGuessCount = 3;
			categories = new Categories();
		}

		public void run() {

			try {
				in = new ObjectInputStream(connection.getInputStream());
				out = new ObjectOutputStream(connection.getOutputStream());
				connection.setTcpNoDelay(true);
			} catch (Exception e) {
				System.out.println("Streams not open");
			}

			while (true) {

				try {
					byte b = in.readByte();
					if (b == 1) {
						won = false;
						out.writeByte(1);
						letterInWord = false;
						String guess = in.readObject().toString();
						ArrayList<Integer> positions = new ArrayList<>();
						callback.accept("client: " + count + " sent: " + guess);

						if (guess.length() == 1) {
							for (int i = 0; i < word.length(); i++) {
								if (word.toLowerCase().charAt(i) == guess.toLowerCase().charAt(0)) {
									letterInWord = true;
									positions.add(i);
								}
							}
							guessCount--;
						} else {
							if (guess.toLowerCase().equals(word.toLowerCase())) {
								won = true;
							}
							wordGuessCount--;
						}
						if (!won) {
							GameMessage msgSend = new GameMessage(positions, guessCount, letterInWord, letterCount,
									wordGuessCount);
							out.writeObject(msgSend);
						} else {
							// player won game
							GameMessage msgSend = new GameMessage(null, -10, false, letterCount, wordGuessCount); // -10
																													// means
																													// we
																													// win
							out.writeObject(msgSend);
							categories.getCategories().remove(currCategory); // have to remove word and
																				// get a new word from
																				// diff category

						}
					} else if (b == 2) {
						currCategory = in.readObject().toString().toLowerCase();
						rand = (int) Math.random() * categories.getCategories().get(currCategory).size();
						word = categories.getCategories().get(currCategory).remove(rand);
						// categories.getCategories().get(currCategory).remove(rand);
						letterCount = word.length();
						guessCount = 6;
						wordGuessCount = 3;
						System.out.println(word);

					} else {

					}

				} catch (Exception e) {
					e.printStackTrace();
					callback.accept(
							"OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
					clients.remove(this);
					break;
				}
			}
		}// end of run

	}// end of client thread
}
