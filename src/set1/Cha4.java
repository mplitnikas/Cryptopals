package set1;

import java.io.BufferedReader;
import java.io.FileReader;

public class Cha4 {
	
	public static void main (String[] args) {
		String filePath = "4.txt";
		
		String line = "";
		DecodeAttempt best = null;
		double bestScore = -1;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {
				
				DecodeAttempt da = Cha3.freqAnalyze(line);
				if ((da.englishness < bestScore) || (bestScore == -1)) {
					best = da;
					bestScore = da.englishness;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(best.plaintext);
		System.out.println(best.englishness);
	}
		
}
