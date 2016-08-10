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

		String inString = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";
		DecodeAttempt bestGuess = freqAnalyze(inString);
		System.out.println("\nBest guess:");
		System.out.println(bestGuess.plaintext);
		System.out.println((char) bestGuess.keyUsed);
		System.out.println(bestGuess.englishScore);
		
	}

	public static DecodeAttempt freqAnalyze(String inputString) {
		byte[] cypherBytes = Cha1.hexStringToBytes(inputString);
		return freqAnalyze(cypherBytes);
	}
	
	public static DecodeAttempt freqAnalyze(byte[] cypherBytes) {		
		DecodeAttempt best = null;
		double bestScore = -1;
		
		for (int i = 0; i < 256; i++) {
			byte[] hashedBytes = hashWithByte((byte)i, cypherBytes);
			DecodeAttempt da = new DecodeAttempt(hashedBytes);
			
			double currScore = calculateEnglishScore(da.plaintext);
			da.englishScore = currScore;
			da.keyUsed = i;
//			System.out.println(da.plaintext);
//			System.out.println(da.englishScore + " / " + bestScore);
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
	
	public static double calculateEnglishScore (String inputText) {		
		HashMap<Character, Integer> charCounts = countChars(inputText);
		int totalLetters = inputText.length();
		HashMap<Character, Double> letterOccurrances = getExpectedLetterOccurrences(letterFreqs, totalLetters);
		double englishness = compareHistograms(charCounts, letterOccurrances);
		return englishness;
	}
	
	private static HashMap<Character, Double> getExpectedLetterOccurrences(HashMap<Character, Double> letterFreqs, int totalLetters) {
		HashMap<Character, Double> result = new HashMap<>();
		for (Map.Entry<Character, Double> a: letterFreqs.entrySet()) {
			char key = a.getKey();
			double value = (a.getValue() * totalLetters);
			result.put(key, value);
		}
		return result;
	}
	
	public static HashMap<Character, Integer> countChars(String inputString) {
		char[] inputArray = inputString.toLowerCase().toCharArray();
		HashMap<Character, Integer> letterCounts = new HashMap<>();
		for (int i = 0; i < inputArray.length; i++) {
			char currentChar = inputArray[i];
			letterCounts.put(currentChar, letterCounts.getOrDefault(currentChar, 0)+1);
		}
		return letterCounts;
	}
	
	public static double compareHistograms(HashMap<Character, Integer> inputHisto, HashMap<Character, Double> referenceHisto) {
		double totalScore = 0;
		
		Iterator<Character> inputHistoKeysIter = inputHisto.keySet().iterator();
		while (inputHistoKeysIter.hasNext()) {
			char c = inputHistoKeysIter.next();
			double observedValue = inputHisto.getOrDefault(c, 0);
			double expectedValue = referenceHisto.getOrDefault(c, 0.0);
			if (expectedValue == 0.0 && ((c > 31) && (c < 127))) {
//				System.out.println("OK char: " + c + " : " + (int)c);
				totalScore += 100 * observedValue;
				continue;
			}
//			System.out.println(c + ": observed, expected: " + observedValue + ", " + expectedValue);
			double currentDistance = Math.pow((observedValue - expectedValue), 2) / expectedValue;
			totalScore += currentDistance;
		}
		return totalScore;
	}
	
}
