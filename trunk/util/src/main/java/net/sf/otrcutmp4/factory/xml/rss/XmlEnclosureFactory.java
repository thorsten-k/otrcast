package net.sf.otrcutmp4.factory.xml.rss;

import net.sf.otrcutmp4.interfaces.model.Storage;
import net.sf.otrcutmp4.model.xml.rss.Enclosure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlEnclosureFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlEnclosureFactory.class);
	
	public static Enclosure build(Storage storage)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(storage.getId()).append(".mp4");
		
		return build(sb.toString(),storage.getSize(),"video/mp4");
	}
	
	public static Enclosure build(String url, long length, String type)
	{
		Enclosure xml = new Enclosure();
		xml.setUrl(url);
		xml.setLength(length);
		xml.setType(type);
		
		return xml;
	}
}
