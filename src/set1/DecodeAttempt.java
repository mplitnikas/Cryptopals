package set1;


public class DecodeAttempt {
	public String plaintext;
	public double englishness;

	//debug variables
	byte[] cypherBytes;
	int hashChar;
	String cypherText;
	
	public DecodeAttempt (byte[] inBytes) {
		this.plaintext = Cha1.bytesToAscii(inBytes).toLowerCase();
	}
}