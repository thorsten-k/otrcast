package net.sf.otrcutmp4.controller.factory.xml;

import net.sf.otrcutmp4.model.xml.otr.Quality;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
