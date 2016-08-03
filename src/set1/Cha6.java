package set1;

public class Cha6 {
	public static void main (String[] args) {
		String inputOne = "this is a test";
		String inputTwo = "wokka wokka!!!";
		byte[] bytesOne = Cha1.asciiToBytes(inputOne);
		byte[] bytesTwo = Cha1.asciiToBytes(inputTwo);
		System.out.println(hammingDist(bytesOne, bytesTwo));
	}
	
	public static int hammingDist (byte[] a1, byte[] a2) {
		assert (a1.length == a2.length);
		
		int totalDist = 0;
		for (int i = 0; i < a1.length; i++) {
			totalDist += byteHammingDist(a1[i], a2[i]);
		}
		
		return totalDist;
	}
	
	public static int byteHammingDist (int a, int b) {
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
