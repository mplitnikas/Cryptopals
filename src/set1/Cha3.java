package set1;

public class Cha3 {
	
	public static void main(String[] args) {

		String inString = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";
		DecodeAttempt bestGuess = CaesarTools.freqAnalyze(inString);
		System.out.println("\nBest guess:");
		System.out.println(bestGuess.plaintext);
		System.out.println((char) bestGuess.keyUsed);
		System.out.println(bestGuess.englishScore);
		
	}
}
