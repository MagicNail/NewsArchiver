package dataProcessing;

import java.util.*;
import java.io.*;

public class JSONParser {
	
	public static List<Article> parse(String filename){
		List<Article> ans = new ArrayList<Article>();
		try {
			Scanner sc = new Scanner(new File(filename));
			while(sc.hasNext()){
				
			}
		} catch (Exception e) {e.printStackTrace();}
		return ans;
	}
}
