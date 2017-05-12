import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class BananagramsServerThread extends Thread {
    private Socket socket = null;
    private String name;
	private ArrayList<String> words;
    

    public BananagramsServerThread(Socket socket) {
        super("BananagramsServerThread");
        this.socket = socket;
    }
    
    public void run() {

    	
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void removeWord(String word) {
		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).equals(word)) {
				words.remove(i);
			}
		}
	}
	
	public void addWord(String word) {
			words.add(word);
	}
}