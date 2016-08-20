package set1;

import java.io.*;

public class Cha6 {
	public static void main (String[] args) {
		
		String inputText;
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("6.txt")));
			String out;
			while ((out = br.readLine()) != null) {
				sb.append(out);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputText = sb.toString();
		}
		
		byte[] inBytes = Cha1.b64ToBytes(inputText);
		System.out.println(VigenereTools.findRepeatingKey(inBytes));
		
	}
}
