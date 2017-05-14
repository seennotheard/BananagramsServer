import java.net.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String fromUser;

            while ((fromUser = in.readLine()) != null) {
                broadcast(fromUser);
                Pattern pattern = Pattern.compile("\\w+");
                Matcher matcher = pattern.matcher(fromUser);
                while (matcher.find()) {
                	String word = matcher.group();
                    if (BananagramsServer.isWordValid(word)) {
                    	BananagramsServer.removeLetters(Word.createCharCount(word));
                    	words.add(word);
                    }
                }
                //add method for disconnect
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void broadcast(String str) {
    	ArrayList<Socket> clientSockets = BananagramsServer.getClientSockets();
    	
    	for(Socket clientSocket : clientSockets) {
			try (
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				){
					out.println(name + ": " + str);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }	
    	
    }
    
    public ArrayList<String> getWords() {
    	return words;
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