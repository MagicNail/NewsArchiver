package rssReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class BaseParser {

	// names of the XML tags
	public static final String CHANNEL = "channel";
	public static final String PUB_DATE = "pubDate";
	public static final String DATE = "date";
	public static final  String DESCRIPTION = "description";
	public static final  String LINK = "link";
	public static final  String TITLE = "title";
	public static final  String ID = "id";
	public static final  String CONTENT = "content";
	public static final  String ORIGINID = "originId";
	public static final  String ITEM = "item";
	public static final  String LOCATION = "location";
	public static final  String RECORD = "record";
	
	private final URL feedUrl;

	protected BaseParser(String feedUrl){
		try {
			this.feedUrl = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	protected InputStream getInputStream() {
		try {
			return feedUrl.openConnection().getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}