package net.sf.otrcutmp4.factory.xml.rss;

import net.sf.otrcutmp4.model.xml.rss.Language;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLanguageFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlLanguageFactory.class);
	
	public static Language build(String language)
	{
		Language xml = new Language();
		xml.setValue(language);
		return xml;
	}
}
