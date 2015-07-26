package net.sf.otrcutmp4.factory.xml.rss;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.otrcutmp4.interfaces.model.Storage;
import net.sf.otrcutmp4.model.xml.rss.PubDate;

public class XmlPubDateFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlPubDateFactory.class);
	
	private static SimpleDateFormat sdf;
	
    public static PubDate build(Storage storage){return build(storage.getRecord()); }

	public static PubDate build(Date date)
	{
		return build(getSdf().format(date));
	}
	
	public static PubDate build(String pubDate)
	{
		PubDate xml = new PubDate();
		xml.setValue(pubDate);
		return xml;
	}
	
	private static SimpleDateFormat getSdf()
	{
		if(sdf==null){sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z",Locale.US);}
		return sdf;
	}
}
