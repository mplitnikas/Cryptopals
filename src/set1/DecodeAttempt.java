package set1;


public class DecodeAttempt {
	public String plaintext;
	public double englishness;
	
	public DecodeAttempt (byte[] inBytes) {
		this.plaintext = Cha1.bytesToAscii(inBytes).toLowerCase();
	}
	
}