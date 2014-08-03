package dataProcessing;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import rssReader.ContentReader;

import articleMatcher.TrieSearch;


public class Article {
	
	public static final int numRelatedArticlesSaved = 5;
	public static final Set<String> placeNames = new HashSet<String>(Arrays.asList(new String[]{"Auckland","Wellington","Christchurch",
			"Hamilton","Napier","Hastings","Tauranga","Dunedin","Palmerston North","Nelson","Rotorua","New Plymouth","Whangarei",
			"Invercargill","Whanganui","Gisborne"}));
	
	private String title;
	private String description;
	private String link;
	private String category;
	private String content;
	private String date;
	private int originId;
	private List<String> related = new ArrayList<String>();
	private List<String> locations = new ArrayList<String>();
	
	public int getOriginId() {
		return originId;
	}
	public void setOriginId(String originId) {
		this.originId = Integer.parseInt(originId);
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
		String[] split = link.split("/");
		category = split[3] + (isInteger(split[4]) ? "" : "/" + split[4]);
	}
	public void contentFromLink(){
		content = ContentReader.readContent(link);
		scanForLocations();
	}
	public String getCategory() {
		return category;
	}
	public String getContent() {
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
		scanForLocations();
	}
	
	public List<String> getRelated(){
		return related;
	}
	
	public void setRelated(List<String> related){
		this.related = related;
	}
	
	public String getJSON(){
		return 	"{\n"+
				"	\"title\": \""+title+"\",\n"+
				"	\"description\": \""+description+"\",\n"+
				"	\"link\": \""+link+"\",\n"+
				"	\"category\": \""+category+"\",\n"+
				"	\"content\": \""+content+"\",\n"+
				"	\"date\": \""+date+"\"\n"+
				"	\"originId\": \""+originId+"\"\n"+
				"}";
	}
	
	public String getXml(){
		String ans = "	<record>\n"+
				"		<title>"+title+"</title>\n"+
				"		<description>"+description+"</description>\n"+
				"		<link>"+link+"</link>\n"+
				"		<category>"+category+"</category>\n"+
				"		<content>"+content+"</content>\n"+
				"		<date>"+date+"</date>\n"+
				"		<originId>"+originId+"</originId>\n"+
				"		<related>\n";
		for(String s : related){
			ans += "			<related>"+s+"</related>\n";
		}
		ans +=  "		</related>\n"+
				"		<locations>\n";
		for(String s : locations){
			ans += "			<location>"+s+"</location>\n";
		}
		ans +=  "		</locations>\n"+
			    "	</record>\n";
		return ans;
	}
	
	public String getHtml(){
		try {
			SimpleDateFormat in = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzzz"); //Fri, 24 May 2013 11:55:00 +1200
			SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//System.out.println(in.parseObject(date)+" "+out.format(in.parse(date)));
			StringBuilder ans = new StringBuilder();
			ans.append("data[Article][title]="+URLEncoder.encode(title,"UTF-8")+
					"&data[Article][description]="+URLEncoder.encode(description,"UTF-8")+
					"&data[Article][link]="+URLEncoder.encode(link,"UTF-8")+
					"&data[Article][category]="+URLEncoder.encode(category,"UTF-8")+
					"&data[Article][content]="+URLEncoder.encode(content,"UTF-8")+
					"&data[Article][date]="+URLEncoder.encode(out.format(in.parse(date)),"UTF-8"));
			if(!locations.isEmpty()) ans.append("&data[Location][Location]=");
			for(String s : locations) ans.append("&data[Location][Location][]="+URLEncoder.encode(s,"UTF-8"));
			ans.append("&data[Related][Related]=");
			for(String s : related) ans.append("&data[Related][Related][]="+URLEncoder.encode(s,"UTF-8"));
			return ans.toString();
		} catch (UnsupportedEncodingException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	//Utilities
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	public void scanForLocations(){
		locations = new ArrayList<String>();
		Scanner sc = new Scanner(content);
		while(sc.hasNext()){
			String s = sc.next();
			if(placeNames.contains(s)){
				if(!locations.contains(s)) locations.add(s);
			}
		}
	}
	
}
