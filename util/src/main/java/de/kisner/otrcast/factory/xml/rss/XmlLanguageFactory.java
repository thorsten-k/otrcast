package de.kisner.otrcast.factory.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.rss.Language;

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
