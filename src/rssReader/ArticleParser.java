package rssReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import dataProcessing.Article;

public class ArticleParser {
	
	private FileInputStream in;

	public ArticleParser(String filename){
		try {
			in = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<Article> parse() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			ArticleHandler handler = new ArticleHandler();
			parser.parse(in, handler);
			return handler.getArticles();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
}