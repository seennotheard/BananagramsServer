import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BananagramsClient {
	
	private static ArrayList<Player> playerList = new ArrayList<Player>();
	
    public static void main(String[] args) throws IOException {
        
        if (args.length != 2) {
            System.err.println(
                "Usage: java BananagramsClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        ) {
        	new BananagramsClientThread(socket).start();
            String fromServer;
            while (true) {
            	while ((fromServer = in.readLine()) == null) {
            	}
                System.out.println(fromServer);
                
                if (fromServer.length() >= 20 && fromServer.substring(0, 20).equals("Server: Player List:")) {
                	Pattern pattern = Pattern.compile("\\w+");
                    Matcher matcher = pattern.matcher(fromServer.substring(20));
                    while (matcher.find()) {
                    	playerList.add(new Player(matcher.group()));
                    }
                }
                else if (fromServer.length() >= 30 && fromServer.substring(0, 30).equals("Server: Player obtained word: ")) {
                	Pattern pattern = Pattern.compile("\\w+");
                    Matcher matcher = pattern.matcher(fromServer.substring(30));
                    matcher.find();
                    String player = matcher.group();
                    matcher.find();
                    for (Player p : playerList) {
                    	if (p.getName().equals(player)) {
                    		p.addWord(matcher.group());
                    		break;
                    	}
                    }
                    displayWords();
                }
                else if (fromServer.length() >= 21 && fromServer.substring(0, 21).equals("Server: Word Stolen: ")) {
                	Pattern pattern = Pattern.compile("\\w+");
                    Matcher matcher = pattern.matcher(fromServer.substring(21));
                    matcher.find();
                    String player = matcher.group();
                    matcher.find();
                    for (Player p : playerList) {
                    	if (p.getName().equals(player)) {
                    		p.removeWord(matcher.group());
                    		break;
                    	}
                    }
                    displayWords();
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
    
    private static void displayWords() {
    	for (Player player : playerList) {
    		System.out.println(player.getName() + ":");
    		for (String word : player.getWords()) {
    			System.out.println(word);
    		}
    	}
    }
}