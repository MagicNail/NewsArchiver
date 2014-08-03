package rssReader;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import dataProcessing.Article;

public class RssParser extends BaseParser {

	public RssParser(String feedUrl){
		super(feedUrl);
	}
	
	public List<Article> parse() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			RssHandler handler = new RssHandler();
			parser.parse(this.getInputStream(), handler);
			return handler.getArticles();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
}