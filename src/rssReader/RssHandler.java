package rssReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import dataProcessing.Article;

import static rssReader.BaseParser.*;


public class RssHandler extends DefaultHandler{
	private List<Article> articles;
	private Article currentArticle;
	private StringBuilder builder;
	
	private boolean headerSkipped = false;
	
	public List<Article> getArticles(){
		return this.articles;
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		builder.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		super.endElement(uri, localName, name);
		if (this.currentArticle != null){
			if (name.equalsIgnoreCase(TITLE)){
				currentArticle.setTitle(treat(builder));
			} else if (name.equalsIgnoreCase(LINK)){
				currentArticle.setLink(treat(builder));
				currentArticle.contentFromLink();
			} else if (name.equalsIgnoreCase(DESCRIPTION)){
				currentArticle.setDescription(treat(builder));
			} else if (name.equalsIgnoreCase(PUB_DATE)){
				currentArticle.setDate(treat(builder));
			} else if (name.equalsIgnoreCase(ITEM)){
				articles.add(currentArticle);
			}
			builder.setLength(0);	
		}
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		articles = new ArrayList<Article>();
		currentArticle = null;
		builder = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);
		if (name.equalsIgnoreCase(ITEM)){
			this.currentArticle = new Article();
			currentArticle.setOriginId(attributes.getValue(ID));
			builder.setLength(0);
		}
	}
	
	private String treat(StringBuilder original){
		Scanner lineScan = new Scanner(original.toString());
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
		return out.toString().trim();
	}
}