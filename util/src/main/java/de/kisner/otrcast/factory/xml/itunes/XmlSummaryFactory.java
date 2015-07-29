package de.kisner.otrcast.factory.xml.itunes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.itunes.Summary;

public class XmlSummaryFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlSummaryFactory.class);
	
	public static Summary build(String summary)
	{
		Summary xml = new Summary();
		xml.setValue(summary);
		return xml;
	}
}
