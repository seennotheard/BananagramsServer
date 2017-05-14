import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class BananagramsServerThread extends Thread {
    private Socket socket = null;
    private String username = null;
	private ArrayList<String> words = new ArrayList<String>();
    

    public BananagramsServerThread(Socket socket) {
    	
        super("BananagramsServerThread");
        this.socket = socket;
    }
    
    public void run() {
    	
        try {
        	PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String fromUser;
            out.println("Please enter a username.");
            while ((fromUser = in.readLine()) == null) {
        	}
            username = fromUser;
            while(true) {
            	while ((fromUser = in.readLine()) == null) {
            		pause(0.01);
                }
            	broadcast(fromUser);
                Pattern pattern = Pattern.compile("\\w+");
                Matcher matcher = pattern.matcher(fromUser);
                while (matcher.find()) {
                	String word = matcher.group();
                    if (word != null && BananagramsServer.isWordValid(word, this)) {
                    	BananagramsServer.removeLetters(Word.createCharCount(word));
                    	BananagramsServer.broadcast("Player obtained word: " + username + ": " + word);
                    	words.add(word);
                    }
                }
                //add method for disconnect
            }
            //out.flush();
            //in.flush();
            //socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void broadcast(String str) {
    	ArrayList<Socket> clientSockets = BananagramsServer.getClientSockets();
    	
    	for(Socket clientSocket : clientSockets) {
			try {
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				out.println(username + ": " + str);
				out.flush();
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
	
	public String getUsername() {
		return username;
	}
	
	private static void pause (double seconds) {
        Date start = new Date();
        Date end = new Date();
        while (end.getTime() - start.getTime() < seconds * 1000) {
            end = new Date();
        }
    }
}