package net.sf.otrcutmp4.factory.xml.rss;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.otrcutmp4.model.xml.rss.PubDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlPubDateFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlPubDateFactory.class);
	
	private static SimpleDateFormat sdf;
	
	public static PubDate build(Date date)
	{
		PubDate xml = new PubDate();
		xml.setValue(getSdf().format(date));
		return xml;
	}
	
	private static SimpleDateFormat getSdf()
	{
		if(sdf==null){sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");}
		return sdf;
	}
}
