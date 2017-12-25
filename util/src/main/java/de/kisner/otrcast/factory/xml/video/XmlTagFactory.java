package de.kisner.otrcast.factory.xml.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.video.Tag;

public class XmlTagFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlTagFactory.class);
	
	public static Tag build(long id)
	{
		Tag xml = new Tag();
		xml.setId(id);
		return xml;
	}
}