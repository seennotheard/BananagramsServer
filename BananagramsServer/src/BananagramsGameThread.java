import java.util.Date;

public class BananagramsGameThread extends Thread {
	
	public BananagramsGameThread() {
        super("BananagramsGameThread");
    }
	
	public void run() {
		//add broadcasts after no more letters
		while (BananagramsServer.getLetterPool().size() != 0) {
			BananagramsServer.flip();
			pause(20);
		}
		pause(40);
		//end game
	}
	
	private static void pause (double seconds) {
        Date start = new Date();
        Date end = new Date();
        while (end.getTime() - start.getTime() < seconds * 1000) {
            end = new Date();
        }
    }
	
	
}
