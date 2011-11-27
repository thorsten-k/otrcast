package net.sf.otrcutmp4.controller.factory.xml;

import net.sf.otrcutmp4.model.xml.otr.OtrId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlOtrIdFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlOtrIdFactory.class);
	
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
