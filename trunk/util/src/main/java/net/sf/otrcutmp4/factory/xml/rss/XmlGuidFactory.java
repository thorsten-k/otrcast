package net.sf.otrcutmp4.factory.xml.rss;

import net.sf.otrcutmp4.interfaces.model.Storage;
import net.sf.otrcutmp4.model.xml.rss.Guid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlGuidFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlGuidFactory.class);
	
	public static Guid build(Storage storage)
	{
		Guid xml = new Guid();
		xml.setValue(storage.getHash());
		xml.setIsPermaLink(false);
		return xml;
	}
}
