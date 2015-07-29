package de.kisner.otrcast.factory.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.otr.Format;

public class XmlFormatFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlFormatFactory.class);
		
	public static Format create(String format)
	{
		String qualityType;
		logger.debug(format);
		if(format.startsWith(XmlQualityFactory.qHD))
		{
			qualityType=XmlQualityFactory.qHD;
			format = format.substring(XmlQualityFactory.qHD.length()+1,format.length());
		}
		else if(format.startsWith(XmlQualityFactory.qHQ))
		{
			qualityType=XmlQualityFactory.qHQ;
			format = format.substring(XmlQualityFactory.qHQ.length()+1,format.length());
		}
		else if(format.contains("mp4")){qualityType=XmlQualityFactory.qMobile;}
		else {qualityType=XmlQualityFactory.qDivX;}
		logger.debug("->"+qualityType);
		
		Format xml = new Format();
		xml.setQuality(XmlQualityFactory.createFromType(qualityType));
		
		int index = format.lastIndexOf(".otrkey");
		if(index>0)
		{
			xml.setOtrkey(true);
			format = format.substring(0,index);
		}
		else{xml.setOtrkey(false);}
		
		index = format.lastIndexOf("cut.");
		if(index>=0)
		{
			xml.setCut(true);
			format = format.substring(0,index)+format.substring(index+4,format.length());
		}
		else{xml.setCut(false);}
		
		xml.setType(format);
		
		return xml;
	}
}
