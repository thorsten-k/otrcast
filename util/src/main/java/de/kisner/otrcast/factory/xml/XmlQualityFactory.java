package de.kisner.otrcast.factory.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.model.xml.otr.Quality;

public class XmlQualityFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlOtrIdFactory.class);
		
	public static final String qMobile = "MP4";
	public static final String qDivX = "DivX";
	public static final String qHQ = "HQ";
	public static final String qHD = "HD";
	
	public static Quality createFromType(String type)
	{
		Quality xml = new Quality();
		xml.setType(type);
		return xml;
	}
}
