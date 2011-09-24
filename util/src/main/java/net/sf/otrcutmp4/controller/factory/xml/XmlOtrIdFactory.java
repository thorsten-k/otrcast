package net.sf.otrcutmp4.controller.factory.xml;

import net.sf.otrcutmp4.model.xml.otr.OtrId;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XmlOtrIdFactory
{	
	static Log logger = LogFactory.getLog(XmlOtrIdFactory.class);
	
	private static final String tag = ".mpg.";
	
	public static OtrId create(String fileId)
	{		
		OtrId xml = new OtrId();
		
		int mpgIndex = fileId.indexOf(tag);
		
		xml.setKey(fileId.substring(0,mpgIndex));
		xml.setFormat(XmlFormatFactory.create(fileId.substring(mpgIndex+tag.length(),fileId.length())));
		
		return xml;
	}
}
