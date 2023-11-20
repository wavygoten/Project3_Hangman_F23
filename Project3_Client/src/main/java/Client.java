import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread {

	Socket socketClient;

	ObjectOutputStream out;
	ObjectInputStream in;

	private Consumer<Serializable> callback;
	private int port;

	Client(Consumer<Serializable> call, int p) {
		port = p;
		callback = call;
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

		while (true) {

			try {
				GameMessage msg = (GameMessage) in.readObject();
				callback.accept(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void send(String data) {

		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
