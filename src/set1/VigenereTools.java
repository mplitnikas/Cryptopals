package set1;

import java.util.*;

public class VigenereTools {
	
	public static String findKeysize (byte[] cypherBytes) {
		
		int keymax = 40;
		if (cypherBytes.length < 80) {
			keymax = cypherBytes.length / 2;
		}
		
		ArrayList<KeysizeGuess> keysizeGuesses = new ArrayList<>(); // change to sortedlist?
		
		for (int i = 0; i < keymax; i++) {
			byte[][] trialMatrix = transposeMatrix(breakIntoBlocks(cypherBytes, i));
			KeysizeGuess ks = new KeysizeGuess(i);
			//ks.setFriedmanScore(friedmantest);
			//ks.setKasiskiScore(kasiskitest);
			keysizeGuesses.add(ks);
			
		}
		// return top (num) keysizes
	    return null;
	}
	
	public static double kasiskiExam (byte[][] inputMatrix) {
		//TODO
		// compare each block with those below via hamming distance
		// return sum of all distances
		// lower is better
		return 0.0;
	}
	
	public static double friedmanTest (byte[][] inputMatrix) {
		//TODO
		return 0.0;		
	}
	
	
	public static byte[][] transposeMatrix (byte[][] source) {
		int cols = source.length;
		int rows = source[0].length;
		byte[][] output = new byte[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				output[i][j] = source[j][i];
			}
		}
		return output;
	}
	
	private static byte[][] breakIntoBlocks (byte[] source, int blockSize) {
		// break byte array into 2d array with 'repetitions' rows of 'blockSize' width
		int len = source.length;
		int repetitions = (int) Math.ceil((double)len / (double)blockSize);
		byte[][] output = new byte[repetitions][blockSize];
		for (int i = 0; i < repetitions; i++) {
			output[i] = Arrays.copyOfRange(source, i*blockSize, blockSize + i*blockSize);
		}
		return output;
	}
	
	public static int hammingDist (byte[] a1, byte[] a2) {
		assert (a1.length == a2.length);
		int totalDist = 0;
		for (int i = 0; i < a1.length; i++) {
			totalDist += hammingDist(a1[i], a2[i]);
		}
		return totalDist;
	}
	
	public static int hammingDist (int a, int b) {
		int res = Cha2.xorHashBytes(a, b);
		return hammingWeight(res);
	}
	
	public static int hammingWeight (int b) {
		if (b == 0) return 0;
		int res = 0;
		if (b % 2 == 1) {
			res = 1;
		}
		b = (b >>> 1);
		return hammingWeight(b) + res;
	}
	
}
