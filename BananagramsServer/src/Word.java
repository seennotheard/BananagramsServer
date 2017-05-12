
public class Word {
	private String str = "";
	private byte[] charCount = new byte[26];
	
	public Word(String str, byte[] charCount) {
		this.str = str;
		this.charCount = charCount;
	}
	
	public Word(String str) {
		this.str = str;
		
		this.charCount = createCharCount(str);
	}
	
	public static byte[] createCharCount(String str) {
		byte [] result = new byte[26];
		
		for (int i = 0; i < str.length(); i++) {
			result[str.charAt(i) - 97]++;
		}
		return result;
	}
	
	
	public String getWord() {
		return this.str;
	}
	
	public byte[] getCharCount() {
		return this.charCount;
	}
	
	public static boolean isWithin(Word foo, Word bar) {
		byte[] fooChars = foo.getCharCount();
		byte[] barChars = bar.getCharCount();
		
		for (int i = 0; i < 26; i++) {
			if (barChars[i] > fooChars[i])
				return false;
		}
		return true;
	}
	// returns if bar is within foo
	public static boolean isWithin(byte[] foo, byte[] bar) {
		for (int i = 0; i < 26; i++) {
			if (bar[i] > foo[i])
				return false;
		}
		return true;
	}
	
	public static byte[] difference(Word foo, Word bar) {
		byte[] fooChars = foo.getCharCount();
		byte[] barChars = bar.getCharCount();
		
		for (int i = 0; i < 26; i++) {
			if (barChars[i] < fooChars[i])
				barChars[i] = 0;
			else
				barChars[i] -= fooChars[i];
		}
		return barChars;
	}
	
	public static byte[] difference(byte[] foo, byte[] bar) {
		for (int i = 0; i < 26; i++) {
			if (bar[i] < foo[i])
				bar[i] = 0;
			else
				bar[i] -= foo[i];
		}
		return bar;
	}
	
	public static byte[] add(byte[] foo, byte[] bar) {
		for (int i = 0; i < 26; i++) {
			bar[i] += foo[i];
		}
		return bar;
	}
}