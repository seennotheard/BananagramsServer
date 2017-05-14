import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class BananagramsServer {
	
	private static ArrayList<Character> letterPool = new ArrayList<Character>(); 
	private static byte[] currentChars = new byte[26];
	private static ArrayList<BananagramsServerThread> playerThreads = new ArrayList<BananagramsServerThread>();
	private static ArrayList<Socket> clientSockets = new ArrayList<Socket>();
	
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
    
    fillLetterPool();
        
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            for (int i = 0; i < numberOfPlayers; i++) {
            	clientSockets.add(serverSocket.accept());
	            playerThreads.add(new BananagramsServerThread(clientSockets.get(i)));
	            playerThreads.get(i).start();
	        }
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
        
        
    }
    
    private static void fillLetterPool() {
    	byte[] charCount = {13, 3, 3, 6, 18, 3, 4, 3, 12, 2, 2, 5, 3, 8, 11, 3, 2, 9, 6, 9, 6, 3, 3, 2, 3, 2};
    	for (int i = 0; i < charCount.length; i++) {
    		for (int j = 0; j < charCount[i]; i++) {
    			letterPool.add((char) ('a' + i));
    		}
    	}
    }
    
    public static ArrayList<Character> getLetterPool() {
    	return letterPool;
    }
    
    public static void removeLetters(byte[] chars) {
    	currentChars = Word.difference(chars, currentChars);
    }
    
    public static void broadcast(String str) {
    	
    	for(Socket clientSocket : clientSockets) {
			try (
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				){
					out.println("Server: " + str);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }	
    	
    }
    
    public static ArrayList<Socket> getClientSockets() {
    	return clientSockets;
    }
    
    public static void flip() {
    	char c = (char) (letterPool.remove((int)(Math.random() * letterPool.size())) - 'a');
    	currentChars[(int) c]++;
    	
    	String currentCharsString = "";
    	for (int i = 0; i < 26; i++) {
			for (byte j = 0; j < currentChars[i]; j++) {
				currentCharsString += (char) (i + 97) + " ";
			}
		}
    	broadcast("The letter pool currently is: " + currentCharsString.trim());
    }
    
    public static boolean isWordValid(String str) {
    	//todo: check from dictionary
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