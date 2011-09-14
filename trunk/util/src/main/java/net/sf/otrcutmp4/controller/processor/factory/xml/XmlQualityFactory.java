package net.sf.otrcutmp4.controller.processor.factory.xml;

import net.sf.otrcutmp4.model.xml.otr.Quality;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XmlQualityFactory
{	
	static Log logger = LogFactory.getLog(XmlOtrIdFactory.class);
	
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
