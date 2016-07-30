package set1;

import java.util.*;

public class DecodeAttempt {
	public String plaintext;
	public SortedSet<Map.Entry<Character, Integer>> topChars;
	
	public DecodeAttempt (byte[] inBytes, int numTopChars) {
		this.plaintext = Cha1.bytesToAscii(inBytes).toLowerCase();
		this.topChars = findTopChars();
	}
	
	private SortedSet<Map.Entry<Character, Integer>> findTopChars () {
		
		if (this.plaintext.equals("")) return null;
		HashMap<Character, Integer> letterCounts = countLetters(this.plaintext);
		
		SortedSet<Map.Entry<Character, Integer>> sortedCounts = 
				new TreeSet<Map.Entry<Character, Integer>>(
						new Comparator<Map.Entry<Character, Integer>>(){
							@Override
							public int compare(Map.Entry<Character, Integer> e1, Map.Entry<Character, Integer> e2) {
								int res = e2.getValue() - e1.getValue();
								if (res == 0) {
									return e1.getKey().compareTo(e2.getKey());
								}
								return res;
							}
						});
		
		sortedCounts.addAll(letterCounts.entrySet());
		return sortedCounts;
	}
	
	private static HashMap<Character, Integer> countLetters(String inputString) {
		char[] inputArray = inputString.toLowerCase().toCharArray();
		
		HashMap<Character, Integer> letterCounts = new HashMap<>();
		
		for (int i = 0; i < inputArray.length; i++) {
			char currentChar = inputArray[i];
			letterCounts.put(currentChar, letterCounts.getOrDefault(currentChar, 0)+1);
		}
		return letterCounts;
	}
	
}