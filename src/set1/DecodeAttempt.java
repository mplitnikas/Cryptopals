package set1;


public class DecodeAttempt implements Comparable<DecodeAttempt> {
	public String plaintext;
	public double englishScore; // lower is better
	public int keyUsed;
	
	public DecodeAttempt (byte[] inBytes) {
		if (inBytes != null) {
			this.plaintext = Cha1.bytesToAscii(inBytes);
		} else {
			this.plaintext = "";
		}
	}
	
	public DecodeAttempt (String inString) {
		this.plaintext = inString;
	}
	
	public int compareTo (DecodeAttempt otherObj) {
		double a = this.englishScore;
		double b = otherObj.englishScore;
		if (a > b) {
			return -1;
		} else if (b > a) {
			return 1;
		} else {
			return 0;
		}	
	}
	
	@Override
	public String toString () {
		return this.plaintext;
	}
}