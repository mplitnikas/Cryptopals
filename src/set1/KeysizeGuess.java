package set1;

import java.util.*;

public class KeysizeGuess implements Comparable<KeysizeGuess> {
	public int keylength;
	public double kasiskiScore = -1;
	public double friedmanScore = -1;
	
	public KeysizeGuess(int i) {
		this.keylength = i;
	}
	
	public double overallScore; // not sure about these two
	public ArrayList<Integer> factors;
	
	public void setKasiskiScore (double score) {
		this.kasiskiScore = score;
		if (this.friedmanScore != -1) {
			this.overallScore = calculateOverallScore();
		}
		//TODO should update factors
	}
	
	public void setFriedmanScore (double score) {
		this.friedmanScore = score;
		if (this.friedmanScore != -1) {
			this.overallScore = calculateOverallScore();
		}
		//TODO should update factors
	}
	
	private double calculateOverallScore () {
		// TODO
		return 0.0;
	}
	
	@Override
	public int compareTo (KeysizeGuess a) {
		return 0; //TODO
	}
	
}
