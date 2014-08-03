package dataProcessing;
import java.io.*;
import java.net.*;
import java.util.*;

import articleMatcher.TrieSearch;

import rssReader.ArticleParser;
import rssReader.RssParser;


public class NewsScraper {

	private List<Article> articles;
	private Set<Integer> idSet;

	public NewsScraper() {
		articles = new ArticleParser("src/dataProcessing/articles.xml").parse();
		idSet = new HashSet<Integer>();
		for(Article a : articles) idSet.add(a.getOriginId());
		List<Article> newArticles = new RssParser("http://www.stuff.co.nz/rss/").parse();
		List<Article> seenArticles = new ArrayList<Article>();
		for(Article a : newArticles) if(idSet.contains(a.getOriginId())) seenArticles.add(a);
		for(Article a : seenArticles) newArticles.remove(a);
		//System.out.println(newArticles);
		for(Article a : newArticles) if(!idSet.contains(a.getOriginId())) articles.add(a);
		for(int i=0;i<newArticles.size();i++){
			TrieSearch.setMostRelated(newArticles.get(i), articles, 5);
		}
		try {
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("src/dataProcessing/articles.xml"),"UTF8");
			out.write("<document>\n");
			for(Article a : articles) out.write(a.getXml());
			out.write("</document>\n");
			out.flush();
			out.close();
		} catch (IOException e) {e.printStackTrace();}
		//System.out.println(articles.get(0).getRelated());
		URL url; 
		try { 
			for(Article a: newArticles){
				// Set up & establish connection 
				url = new URL("http://timeline.vidulich.co.nz/articles/add");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
				conn.setReadTimeout(100000); 
				conn.setConnectTimeout(150000); 
				conn.setRequestMethod("POST"); 
				conn.setDoInput(true); 
				conn.setDoOutput(true); 

				// Send data 
				OutputStream os = conn.getOutputStream(); 
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, 
						"UTF-8")); 
				//System.out.println(a.getHtml());
				bw.write(a.getHtml());
				bw.close(); 
				os.close(); 

				// Get response 
				System.out.println(conn.getResponseCode()+": "+conn.getResponseMessage()); 
			}
		} catch (MalformedURLException e) { 
			System.out.println("PostTask "+e.getMessage()); 
		} catch (IOException e) { 
			System.out.println("PostTask "+e.getMessage()); 
		} 
	}

	public static void main(String[] args) {
		new NewsScraper();
	}
}
