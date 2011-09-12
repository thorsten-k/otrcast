package net.sf.otrcutmp4.controller.processor.factory.xml;

import net.sf.otrcutmp4.model.xml.otr.Format;

public class XmlFormatFactory
{	
	public static Format create(String format)
	{
		Format xml = new Format();
		xml.setType(format);
		
		return xml;
	}
}
