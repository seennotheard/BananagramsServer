import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BananagramsClientThread extends Thread {
	private Socket socket = null;
	
	public BananagramsClientThread (Socket socket) {
		super("BananagramsClientThread");
		this.socket = socket;
	}
	
	public void run() {
		try (
	            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        ) {
				Scanner scanner = new Scanner(System.in);
				String str = scanner.next();
	            while ((str = in.readLine()) != null) {
	                out.println(str);
	                //add method for disconnect
	            }
	            scanner.close();
	            socket.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
}
