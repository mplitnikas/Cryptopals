package set1;

import java.util.*;

public class Cha3 {

	// put together a map of letters and their English frequencies
	private static final char[] letterKeys = {'e', 't', 'a', 'o', 'i', 'n', 's', 'r', 'h', 'd', 'l', 'u', 'c', 'm', 'f', 'y', 'w', 'g', 'p', 'b', 'v', 'k', 'x', 'q', 'j', 'z'};
	private static final double[] freqValues = {0.1202, 0.091, 0.0812, 0.0768, 0.0731, 0.0695, 0.0628, 0.0602, 0.0592, 0.0432, 0.0398, 0.0288, 0.0271, 0.0261, 0.023, 0.0211, 0.0209, 0.0203, 0.0182, 0.0149, 0.0111, 0.0069, 0.0017, 0.0011, 0.001, 0.0007};
	public static final HashMap<Character, Double> letterFreqs;
		static {
			letterFreqs = new HashMap<>();
			for (int i = 0; i < letterKeys.length; i++) {
				letterFreqs.put(letterKeys[i], freqValues[i]);
			}
		}
	
	public static void main(String[] args) {

//		String inString = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";
//		DecodeAttempt bestGuess = freqAnalyze(inString);
//		System.out.println("\nBest guess:");
//		System.out.println(bestGuess.plaintext);
//		System.out.println(bestGuess.score);
		
		
	}

	public static double englishScore (String inputText) {
		
		
		return 0.0;
	}
	
	public static DecodeAttempt freqAnalyze(String inputString) {
		
		byte[] cypherBytes = Cha1.hexStringToBytes(inputString);
		DecodeAttempt[] decodeAttemptsArray = new DecodeAttempt[26];
		
		for (int i = 0; i < 256; i++) {
			byte[] hashedArray = hashWithByte((byte)i, cypherBytes);
			DecodeAttempt res = new DecodeAttempt(hashedArray);
			decodeAttemptsArray[i-97] = res;
		}
		
		DecodeAttempt best = null;
		double bestScore = -1;
		for (DecodeAttempt da: decodeAttemptsArray) {
			HashMap<Character, Double> currFreqs = calculateFrequencies(da.charCounts);
//			System.out.println("\n\nScoring string: " + da.plaintext);
			double currScore = calculateScore(currFreqs);
			da.englishness = currScore;
//			System.out.println(da.score);
			if ((currScore < bestScore) || (bestScore == -1)) {
				best = da;
				bestScore = currScore; 
			}
		}
		return best;
	}
	
	public static byte[] hashWithByte (byte hashByte, byte[] textBytes) {
		int len = textBytes.length;
		
		byte[] keyArray = new byte[len];
		Arrays.fill(keyArray, hashByte);
		byte[] hashArray = Cha2.hashByteArrays(keyArray, textBytes);		
		
		return hashArray;
	}
	
	public static HashMap<Character, Double> calculateFrequencies (HashMap<Character, Integer> charCounts) {
		
		int totalLetters = 0;
		Iterator<Character> countIter = charCounts.keySet().iterator();
		while (countIter.hasNext()) {
			char currChar = countIter.next();
			if ((currChar >= 'a') && (currChar <= 'z')) {
				totalLetters += charCounts.get(currChar);
			}
		}
		//if (totalLetters == 0) totalLetters = 1;
		HashMap<Character, Double> letterPercentages = new HashMap<>();
		
		Iterator<Character> charCountKeysIter = charCounts.keySet().iterator();
		while (charCountKeysIter.hasNext()) {
			char currChar = charCountKeysIter.next();
			double percentage = ((double)charCounts.get(currChar) / totalLetters);
			letterPercentages.put(currChar, percentage);
		}
		
		return letterPercentages;
	}
	
	public static double calculateScore(HashMap<Character, Double> letterPercentages) {
		
		double totalScore = 0;
		Iterator<Character> letterPercentageKeysIter = letterPercentages.keySet().iterator();
		while (letterPercentageKeysIter.hasNext()) {
			char currChar = letterPercentageKeysIter.next();
			if (((currChar >= 'a') && (currChar <= 'z')) || ((currChar >= 'A') && (currChar <= 'Z'))) {
				// using squared differences
				double currentDistance = Math.pow((letterPercentages.getOrDefault(currChar, 0.0) - letterFreqs.get(currChar)), 2);
//				System.out.println(letterPercentages.getOrDefault(currChar, 0.0));
//				System.out.println(letterFreqs.get(currChar));
				// using absolute value
				//double currentDistance = Math.abs((letterPercentages.getOrDefault(currChar, 0.0) - letterFreqs.get(currChar)));
				totalScore += currentDistance;
//				System.out.println("currChar = " + currChar);
//				System.out.println("Distance: " + currentDistance);
			} else {
				// non-letter character penalty??
				totalScore += 100;
			}
		}
		return totalScore;
	}
	
}
