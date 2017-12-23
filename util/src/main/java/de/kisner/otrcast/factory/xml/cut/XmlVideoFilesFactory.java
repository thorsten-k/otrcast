package de.kisner.otrcast.factory.xml.cut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.model.xml.cut.VideoFiles;

public class XmlVideoFilesFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlOtrIdFactory.class);
			
	public static VideoFiles build() throws OtrProcessingException
	{
		VideoFiles xml = new VideoFiles();
		
		return xml;
	}
}
