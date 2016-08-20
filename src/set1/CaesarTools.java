package set1;

import java.util.*;

public class CaesarTools {

	// put together a map of letters and their English frequencies
	private static final char[] letterKeys = {' ', '!', '"','\'', ',', '-', '.', '1', '2', '5', '6', '7', '9', '?', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	private static final double[] freqValues = {0.1707158516,0.0013195295,0.0078272717,0.0017441579,0.0181552868,0.0032407311,0.0091274369,6.91577280303188E-006,2.76630912121275E-006,1.38315456060637E-006,2.76630912121275E-006,9.68208192424463E-006,1.38315456060637E-006,0.0012628201,0.063976431,0.0105755998,0.0173834865,0.0367241367,0.1004114885,0.01769193,0.0163889984,0.0518724455,0.0512804553,0.0004827209,0.0063472963,0.0287156718,0.0184263851,0.0566540108,0.062042781,0.0125479782,0.0008672379,0.0495376806,0.049738238,0.0703237273,0.0223794408,0.0068881097,0.0183254148,0.0008810695,0.0158246713,0.0002946119};
	public static final HashMap<Character, Double> letterFreqs;
		static {
			letterFreqs = new HashMap<>();
			for (int i = 0; i < letterKeys.length; i++) {
				letterFreqs.put(letterKeys[i], freqValues[i]);
			}
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
			System.out.println(da.plaintext);
			System.out.println(da.englishScore + " / " + bestScore);
			if (currScore > bestScore) {
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
		HashMap<Character, Double> letterProportions = calculateFrequencies(charCounts, totalLetters);
		double englishness = compareHistograms(letterProportions, letterFreqs);
		return englishness;
	}
	
	public static HashMap<Character, Double> calculateFrequencies (HashMap<Character, Integer> charCounts, int totalLetters) {		
		 		HashMap<Character, Double> letterPercentages = new HashMap<>();
		 		Iterator<Character> charCountKeysIter = charCounts.keySet().iterator();
		 		while (charCountKeysIter.hasNext()) {
		 			char currChar = charCountKeysIter.next();
		 			double percentage = ((double)charCounts.get(currChar) / totalLetters);
		 			letterPercentages.put(currChar, percentage);
		 		}
		 		
		 		return letterPercentages;
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
	
	public static double compareHistograms(HashMap<Character, Double> inputHisto, HashMap<Character, Double> referenceHisto) {
		/** 
		 * Returns similarity score of the two histograms containing <Character, Double> proportion information.
		 * Values must be between 0.0 - 1.0 to work properly.
		 * Currently uses a chi test for comparison.
		 * Both histograms should have the same key type and at least some of the same keys.
		 **/
		
		double totalScore = 0;
		Iterator<Character> inputHistoKeysIter = inputHisto.keySet().iterator();
		while (inputHistoKeysIter.hasNext()) {
			char c = inputHistoKeysIter.next();
			double observedValue = inputHisto.get(c);
			double expectedValue = referenceHisto.getOrDefault(c, 0.0);
//			System.out.println(c + ": observed, expected: " + observedValue + ", " + expectedValue);
			double currentDistance = observedValue * expectedValue; // Super easy! Higher total score = better.
			totalScore += currentDistance;
		}
		return totalScore;
	}
	
}
