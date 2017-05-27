import java.util.Date;

public class BananagramsGameThread extends Thread {
	
	public BananagramsGameThread() {
        super("BananagramsGameThread");
    }
	
	public void run() {
		BananagramsServer.flip();
		BananagramsServer.flip();
		while (BananagramsServer.getLetterPool().size() != 0) {
			pause(20);
			BananagramsServer.flip();
		}
		BananagramsServer.broadcast("There are no more unflipped letters. The game will automatically end in 60 seconds.");
		pause(40);
		BananagramsServer.endGame();
	}
	private static void pause (double seconds) {
        Date start = new Date();
        Date end = new Date();
        while (end.getTime() - start.getTime() < seconds * 1000) {
            end = new Date();
        }
    }
}