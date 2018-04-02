package de.kisner.otrcast.factory.xml.otr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.video.tv.Series;

public class XmlOtrFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlOtrFactory.class);
	
	public static Otr build() 
	{
		return new Otr();
	}
	
	public static Otr build(Series series)
	{
		Otr xml = build();
		xml.getSeries().add(series);
		return xml;
	}
}