package set1;

import java.util.*;

public class VigenereTools {
	
	public static String findRepeatingKey (byte[] cypherBytes) {
		
		int keymax;
		if (cypherBytes.length >= 80) {
			keymax = 40;
		} else {
			keymax = cypherBytes.length / 2;
		}
		
		// a sortedset that compares based on values of entries
		SortedSet<Map.Entry<Integer, Integer>> keysizesBest = new TreeSet<Map.Entry<Integer, Integer>>(
				new Comparator<Map.Entry<Integer, Integer>>() {
					@Override
					public int compare(Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) {
						int comp = e1.getValue().compareTo(e2.getValue());
						if (comp == 0) {
							return e1.getKey().compareTo(e2.getKey());
						} else {
							return comp;
						}
					}
				});
		
		Map<Integer, Integer> keysizeMap = new TreeMap<>();
	    for (int keysize = 2; keysize <= keymax; keysize++) {
	    	byte[] a1 = Arrays.copyOfRange(cypherBytes, 0, keysize);
	    	byte[] a2 = Arrays.copyOfRange(cypherBytes, keysize, keysize*2);
	    	int score = hammingDist(a1, a2) / keysize;
	    	keysizeMap.put(keysize, score);
	    }
	    
	    keysizesBest.addAll(keysizeMap.entrySet());
	    Iterator<Map.Entry<Integer, Integer>> keysizeCandidateIter = keysizesBest.iterator();
	    
	    int numCandidates = 10;
	    for (int i = 0; i < numCandidates; i++) {
	    	int keysize = keysizeCandidateIter.next().getKey();
	    	byte[][] chunks = breakIntoBlocks(cypherBytes, keysize); // do this for each of the top (3?) keysizes
			byte[][] singleKeyBlocks = transposeMatrix(chunks);
		
			List<DecodeAttempt> decodedBlocks = new ArrayList<>();
			for (byte[] block : singleKeyBlocks) {
				decodedBlocks.add(CaesarTools.freqAnalyze(block));
			}
	    	
			Iterator<DecodeAttempt> blocksIter = decodedBlocks.iterator();
			StringBuilder keyword = new StringBuilder();
			while (blocksIter.hasNext()) {
				DecodeAttempt next = blocksIter.next();
				char keyChar = (char)next.keyUsed;
				System.out.println(next.englishScore);
				keyword.append(keyChar);
			}
			System.out.println(keyword);
			System.out.println("-------------");
	    }
		
	    return null;
	}
	
	private static byte[][] transposeMatrix (byte[][] source) {
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
