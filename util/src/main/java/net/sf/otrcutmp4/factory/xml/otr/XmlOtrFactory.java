package net.sf.otrcutmp4.factory.xml.otr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.otrcutmp4.model.xml.container.Otr;

public class XmlOtrFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlOtrFactory.class);
	
	public static Otr build() 
	{
		return new Otr();
	}
}
