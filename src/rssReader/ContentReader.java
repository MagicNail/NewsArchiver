package rssReader;

import java.io.*;
import java.net.*;
import java.util.*;

public class ContentReader {
	

	/*String in = new String(ch, start, length);
	StringBuilder out = new StringBuilder();
	Scanner sc = new Scanner(in);
	sc.useDelimiter("[\\s\\t\\n;]+");
	while(sc.hasNext()){
		String token = sc.next();
		if(token.equals("&pound")) token = "£";
		else if(token.equals("&ndash")) token = "–";
		else if(token.equals("&nbsp")) token = " ";
		else if(token.startsWith("&")) token = "";
		out.append(token);
		out.append(" ");
		System.out.println(token);
	}*/
	
	public static String readContent(String url){
		StringBuilder ans = new StringBuilder();
		try {
			Scanner sc = new Scanner(new InputStreamReader(new URL(url).openStream()));
			outer:
			while(sc.hasNextLine()){
				String s = sc.nextLine();
				//System.out.println(s);
				if(s.trim().equals("<!--start of components/story_body_manipulated-->")){
					while(sc.next().trim().equals("<p>")){
						sc.nextLine();
						sc.nextLine();
						String line = sc.nextLine().trim();
						Scanner lineScan = new Scanner(line);
						lineScan.useDelimiter("[\\s\\t\\n;]+");
						StringBuilder out = new StringBuilder();
						while(lineScan.hasNext()){
							String token = lineScan.next();
							if(token.equals("&pound")) token = "£";
							else if(token.equals("&ndash")) token = "–";
							else if(token.equals("&nbsp")) token = " ";
							else if(token.contains("&") || token.contains("<")) token = "";
							out.append(token);
							out.append(" ");
							//System.out.println(token);
						}
						if(!line.startsWith("<")) ans.append(out+"\n");
						sc.nextLine();
					}
					break outer;
				}
			}
		} catch (Exception e) {	e.printStackTrace();}
		//System.out.print(ans);
		return ans.toString();
	}
	
	public static void main(String[] args){
		readContent("http://www.stuff.co.nz/national/8693203/Wellington-trains-stopped-after-derailment");
	}
}
