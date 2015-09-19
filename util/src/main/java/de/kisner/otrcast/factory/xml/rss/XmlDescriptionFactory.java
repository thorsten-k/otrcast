package de.kisner.otrcast.factory.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;
import de.kisner.otrcast.model.xml.rss.Description;

public class XmlDescriptionFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlDescriptionFactory.class);

    public static <SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,
					SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,
					EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,
					COVER extends Image,STORAGE extends Storage>
    		Description build(EPISODE episode)
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
