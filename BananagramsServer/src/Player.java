
import java.util.ArrayList;

public class Player {
	
	private String name;
	private ArrayList<String> words;
	
	public Player(String name) {
		this.name = name;
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
	
	public String getName() {
		return name;
	}
}