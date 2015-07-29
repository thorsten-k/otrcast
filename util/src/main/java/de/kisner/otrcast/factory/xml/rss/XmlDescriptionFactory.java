package de.kisner.otrcast.factory.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.model.xml.rss.Description;

public class XmlDescriptionFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlDescriptionFactory.class);

    public static Description build(Episode episode)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(episode.getSeason().getNr()).append("x").append(episode.getNr());

        return build(sb.toString());
    }

	public static Description build(String description)
	{
		Description xml = new Description();
		xml.setValue(description);
		return xml;
	}
}
