package de.kisner.otrcast.factory.xml.otr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.container.Otr;

public class XmlOtrFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlOtrFactory.class);
	
	public static Otr build() 
	{
		return new Otr();
	}
}
