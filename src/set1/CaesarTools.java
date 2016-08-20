package set1;

import java.util.*;

public class CaesarTools {

	// put together a map of letters and their English frequencies
	private static final char[] letterKeys = {' ', '!', '"','\'', ',', '-', '.', '1', '2', '5', '6', '7', '9', '?', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	private static final double[] freqValues = {0.1673082919,0.0012931911,0.007671036,0.0017093438,0.0177928996,0.0031760448,0.0089452495,6.7777310867414E-006,2.71109243469656E-006,1.35554621734828E-006,2.71109243469656E-006,9.48882352143797E-006,1.35554621734828E-006,0.0012376137,0.0637947161,0.0110314351,0.0179257432,0.0372558322,0.0987894972,0.0178281439,0.0164292202,0.0519973974,0.0539276952,0.0008431497,0.0062816012,0.0291916878,0.0202274607,0.0559976142,0.061154112,0.0128139784,0.0008878828,0.0487427309,0.04984479,0.071021133,0.021982893,0.0068658416,0.0187539819,0.0009027938,0.0160618671,0.0002887313};
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
//			System.out.println(da.plaintext);
//			System.out.println(da.englishScore + " / " + bestScore);
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
