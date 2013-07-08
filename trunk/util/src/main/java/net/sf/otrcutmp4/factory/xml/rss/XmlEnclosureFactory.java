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
		Enclosure xml = new Enclosure();
		xml.setLength(storage.getSize());
		xml.setType("video/mp4");
		xml.setUrl("NYI url");
		return xml;
	}
}
