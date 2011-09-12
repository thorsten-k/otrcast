package net.sf.otrcutmp4.controller.processor.factory.xml;

import net.sf.otrcutmp4.model.xml.otr.OtrId;

public class XmlOtrIdFactory
{	
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
