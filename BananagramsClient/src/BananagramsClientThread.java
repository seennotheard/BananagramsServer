import java.net.*;

public class BananagramsClientThread extends Thread {
	private Socket socket = null;
	
	public BananagramsClientThread (Socket socket) {
		super("BananagramsClientThread");
		this.socket = socket;
	}
	
	public void run() {
		
	}
}
