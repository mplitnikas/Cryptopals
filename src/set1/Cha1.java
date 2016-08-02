package set1;

import java.util.Base64;


public class Cha1 {
	
	private static final char[] hexArray = "0123456789ABCDEF".toCharArray(); 
	
	public static void main(String[] args) {
		String inString = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d";
		String checkString = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t";
		System.out.println(checkString);
		System.out.println(hexToB64(inString));
		System.out.println(hexToAscii(inString));
	}
	
	public static String hexToB64 (String inputNum) {
		return bytesToB64(
						hexStringToBytes(inputNum)
						);
	}
	
	public static byte[] hexStringToBytes (String inputNum) {
		// lifted from stack overflow: http://tinyurl.com/chj5fz
		int len = inputNum.length();
		byte[] result = new byte[len/2];
		for (int i = 0; i < len; i += 2) {
			result[i/2] = (byte) ((Character.digit(inputNum.charAt(i), 16) << 4)	//avoids String methods
									+ Character.digit(inputNum.charAt(i+1), 16));
		}
		return result;
	}
	
	public static String bytesToHexString (byte[] inBytes) {
		int len = inBytes.length;
		StringBuilder sb = new StringBuilder(len * 2);
		
		for (int i = 0; i < inBytes.length; i++) {
			int currByte = inBytes[i] & 0xFF;
			sb.append(hexArray[currByte >> 4]);
			sb.append(hexArray[currByte & 0b00001111]);
		}
		return sb.toString().toLowerCase();
	}
	
	public static String bytesToB64 (byte[] inputBytes) {
		String result = Base64.getEncoder().encodeToString(inputBytes);
		return result;
	}
	
	public static String bytesToAscii (byte[] inputBytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b: inputBytes) {
			sb.append((char) b);
		}
		return sb.toString().replace('\0', ' '); // character \00 causes problems with ending strings in java
	}
	
	public static String hexToAscii (String hexInput) {
		return bytesToAscii(hexStringToBytes(hexInput));
	}
	
}
