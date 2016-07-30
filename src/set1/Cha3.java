package set1;

import java.util.Arrays;

public class Cha3 {

	public static void main(String[] args) {
		/* 
		 * try string against each char (bytes, not letters)
		 * 
		 * for each attempt, return a HashedString object:
		 * 		has properties 'plaintext', 'score', 'top 12 letters'
		 * 
		 * write a scoring algorithm (can be changed later)
		 * 
		 */
		String inString = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";
		
		DecodeAttempt bestGuess = freqAnalyze(inString);
		System.out.println(bestGuess.plaintext);
		
	}

	public static DecodeAttempt freqAnalyze(String inputString) {
		// top-level, return the best analysis wrt score
		
		byte[] cypherBytes = Cha1.hexStringToBytes(inputString);
		DecodeAttempt[] results = new DecodeAttempt[256];
		
		for (byte i = 0; i < 256; i++) {
			byte[] hashedArray = hashWithByte(i, cypherBytes);
			DecodeAttempt res = new DecodeAttempt(hashedArray, 6);
			results[i] = res;
		}
		
		
		
		return results[0]; // change to return the best result
	}
	
	public static byte[] hashWithByte (byte hashByte, byte[] textBytes) {
		int len = textBytes.length;
		
		byte[] keyArray = new byte[len];
		Arrays.fill(keyArray, hashByte);
		byte[] hashArray = Cha2.hashByteArrays(keyArray, textBytes);		
		
		return hashArray;
	}
	
}

