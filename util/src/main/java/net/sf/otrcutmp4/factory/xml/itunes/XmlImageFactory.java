package net.sf.otrcutmp4.factory.xml.itunes;

import net.sf.otrcutmp4.model.xml.itunes.Image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlImageFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlImageFactory.class);
	
	public static Image build(String imageUrl)
	{
		Image xml = new Image();
		xml.setHref(imageUrl);
		return xml;
	}
}
