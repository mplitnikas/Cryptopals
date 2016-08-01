package set1;

import java.util.*;

public class DecodeAttempt {
	public String plaintext;
	public HashMap<Character, Integer> charCounts;
	public double englishness;
	
	public DecodeAttempt (byte[] inBytes) {
		this.plaintext = Cha1.bytesToAscii(inBytes).toLowerCase();
		this.charCounts = countChars(this.plaintext);
	}
	
	private static HashMap<Character, Integer> countChars(String inputString) {
		char[] inputArray = inputString.toLowerCase().toCharArray();
		
		HashMap<Character, Integer> letterCounts = new HashMap<>();
		
		for (int i = 0; i < inputArray.length; i++) {
			char currentChar = inputArray[i];
			letterCounts.put(currentChar, letterCounts.getOrDefault(currentChar, 0)+1);
		}
		return letterCounts;
	}
	
}