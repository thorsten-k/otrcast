package net.sf.otrcutmp4.controller.factory.xml;

import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.model.xml.cut.FileName;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		
		return xml;
	}
}
