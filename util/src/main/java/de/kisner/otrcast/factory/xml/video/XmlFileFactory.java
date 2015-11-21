package de.kisner.otrcast.factory.xml.video;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFileFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlFileFactory.class);
	
	public static de.kisner.otrcast.model.xml.video.File buildPathName(File file)
	{
		de.kisner.otrcast.model.xml.video.File xml = new de.kisner.otrcast.model.xml.video.File();
		xml.setPath(file.getParentFile().getAbsolutePath());
		xml.setName(file.getName());
		return xml;
	}
}
