package set1;


public class DecodeAttempt {
	public String plaintext;
	public double englishScore; // lower is better
	public int keyUsed;
	
	public DecodeAttempt (byte[] inBytes) {
		this.plaintext = Cha1.bytesToAscii(inBytes);
	}
}