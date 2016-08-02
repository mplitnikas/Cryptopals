package set1;

public class Cha5 {
	
	public static void main (String[] args) {
		String inString = "Burning 'em, if you ain't quick and nimble\nI go crazy when I hear a cymbal";
		String key = "ICE";
		System.out.println(encryptRepeatingKeyStrings (inString, key));
	}
	
	public static String encryptRepeatingKeyStrings(String inputText, String key) {
		
		assert (key.length() <= inputText.length());
		
		byte[] inputBytes = Cha1.asciiToBytes(inputText);
		byte[] keyBytes = Cha1.asciiToBytes(key);
		
		byte[] resultBytes = encryptRepeatingKey(inputBytes, keyBytes);
		String outHex = Cha1.bytesToHexString(resultBytes);
		return outHex;
	}
	
	public static byte[] encryptRepeatingKey(byte[] inputBytes, byte[] keyBytes) {
		
		byte[] resultBytes = new byte[inputBytes.length]; 
		for (int i = 0; i < inputBytes.length; i++) {
			byte b1 = (byte)inputBytes[i];
			byte b2 = keyBytes[i % keyBytes.length];
			resultBytes[i] = Cha2.xorHashBytes(b1, b2);
		}
		return resultBytes;
	}
	
}
