package net.sf.otrcutmp4.factory.xml.itunes;

import net.sf.otrcutmp4.model.xml.itunes.Summary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
