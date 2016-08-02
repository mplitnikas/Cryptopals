package set1;



public class Cha2 {
	
	public static void main(String[] args) {
		String inString = "1c0111001f010100061a024b53535009181c";
		String hashString = "686974207468652062756c6c277320657965";
		String checkString = "746865206b696420646f6e277420706c6179";
		
		String resultString = hashHexStrings(inString, hashString); 
		if (checkString.equals(resultString)) {
			System.out.println("It works!");
		} else {
			System.out.println("Failed.");
			System.out.println(checkString);
			System.out.println(resultString);
		}
		
	}
	
	public static String hashHexStrings(String inString, String hashString) {
		
		byte[] textBytes = Cha1.hexStringToBytes(inString);
		byte[] keyBytes = Cha1.hexStringToBytes(hashString);
		
		byte[] hashBytes = hashByteArrays(textBytes, keyBytes);
		
		String result = Cha1.bytesToHexString(hashBytes);
		
		return result;
	}
	
	public static byte[] hashByteArrays (byte[] array1, byte[] array2) {
		
		assert (array1.length == array2.length);
		
		byte[] result = new byte[array1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = xorHashBytes(array1[i], array2[i]);
			//result[i] = (byte) ((byte) array1[i] ^ (byte) array2[i]);
		}
		
		return result;
	}
	
	public static byte xorHashBytes (byte b1, byte b2) {
		return (byte) ((byte) b1 ^ (byte) b2);
	}
	
}