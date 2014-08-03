package articleMatcher;

import java.io.*;
import java.util.*;

public class NounParser {
	
	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(new File("part-of-speech.txt"));
		BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
		while(sc.hasNext()){
			String ln = sc.nextLine();
			String[] split = ln.split("	");
			if(split[1].equals("N")) out.write(split[0]+"\n");
		}
		out.flush();
		out.close();
	}
}
