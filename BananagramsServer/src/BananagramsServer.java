import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class BananagramsServer {
	
	private static ArrayList<Character> letterPool = new ArrayList<Character>(); 
	private static byte[] currentChars = new byte[26];
	private static ArrayList<BananagramsServerThread> playerThreads = new ArrayList<BananagramsServerThread>();
	
    public static void main(String[] args) throws IOException {

    if (args.length != 2) {
        System.err.println("Usage: java BananagramsServer <port number> <number of players>");
        System.exit(1);
    }

    
        int portNumber = Integer.parseInt(args[0]);
        
        int numberOfPlayers = Integer.parseInt(args[1]);
    
    if (numberOfPlayers <= 0) {
    	System.err.println("No players in the game");
    	System.exit(1);
    }
        
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            for (int i = 0; i < numberOfPlayers; i++) {
	            playerThreads.add(new BananagramsServerThread(serverSocket.accept()));
	            playerThreads.get(i).start();
	        }
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
        
        
    }
    
    private void fillLetterPool() {
    	byte[] charCount = {13, 3, 3, 6, 18, 3, 4, 3, 12, 2, 2, 5, 3, 8, 11, 3, 2, 9, 6, 9, 6, 3, 3, 2, 3, 2};
    	for (int i = 0; i < charCount.length; i++) {
    		for (int j = 0; j < charCount[i]; i++) {
    			letterPool.add((char) ('a' + i));
    		}
    	}
    }
    
    private void flip() {
    	currentChars[(int) (letterPool.get((int)(Math.random() * letterPool.size())) - 'a')]++;
    }
    
    private boolean isWordValid(String str) {
    	byte[] word = Word.createCharCount(str);
    	if (Word.isWithin(currentChars, word))
    		return true;
    	for (BananagramsServerThread player : playerThreads) {
    		for(String w : player.getWords()) {
    			if (Word.isWithin(Word.add(currentChars, Word.createCharCount(w)), word))
    				return true;
    		}
    	}
		return false;
    }
    
    
}