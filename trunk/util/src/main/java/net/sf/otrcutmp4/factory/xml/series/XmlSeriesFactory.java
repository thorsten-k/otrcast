package net.sf.otrcutmp4.factory.xml.series;

import net.sf.otrcutmp4.model.xml.series.Series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSeriesFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlSeriesFactory.class);
		
	public static Series create(String format)
	{
		Series xml = new Series();
		
		return xml;
	}
}
