package set1;

public class Cha5 {
	
	public static void main (String[] args) {
		String inString = "Burning 'em, if you ain't quick and nimble\nI go crazy when I hear a cymbal";
		String key = "ICE";
		System.out.println(Cha1.bytesToHexString(encryptRepeatingKey (inString, key)));
	}
	
	public static byte[] encryptRepeatingKey(String inputText, byte[] keyBytes) {
		byte[] inputBytes = Cha1.asciiToBytes(inputText);
		return encryptRepeatingKey(inputBytes, keyBytes);
	}
	
	public static byte[] encryptRepeatingKey(byte[] inputBytes, String key) {
		byte[] keyBytes = Cha1.asciiToBytes(key);
		return encryptRepeatingKey(inputBytes, keyBytes);
	}
	
	public static byte[] encryptRepeatingKey(String inputText, String key) {
		byte[] inputBytes = Cha1.asciiToBytes(inputText);
		byte[] keyBytes = Cha1.asciiToBytes(key);
		return encryptRepeatingKey(inputBytes, keyBytes);
	}
	
	public static byte[] encryptRepeatingKey(byte[] inputBytes, byte[] keyBytes) {
		assert(inputBytes.length == keyBytes.length);
		
		byte[] resultBytes = new byte[inputBytes.length]; 
		for (int i = 0; i < inputBytes.length; i++) {
			byte b1 = (byte)inputBytes[i];
			byte b2 = keyBytes[i % keyBytes.length];
			resultBytes[i] = Cha2.xorHashBytes(b1, b2);
		}
		return resultBytes;
	}
	
}
