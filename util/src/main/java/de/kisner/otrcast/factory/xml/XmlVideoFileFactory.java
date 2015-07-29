package de.kisner.otrcast.factory.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.factory.xml.otr.XmlTvFactory;
import de.kisner.otrcast.model.xml.cut.FileName;
import de.kisner.otrcast.model.xml.cut.VideoFile;

public class XmlVideoFileFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlOtrIdFactory.class);
			
	public static VideoFile create(String fileName) throws OtrProcessingException
	{
		VideoFile xml = new VideoFile();
		
		FileName fName = new FileName();
		fName.setValue(fileName);
		xml.setFileName(fName);
		
		xml.setOtrId(XmlOtrIdFactory.createForFileName(fileName));
		xml.getOtrId().setTv(XmlTvFactory.createForFileName(fileName));
		
		return xml;
	}
}
