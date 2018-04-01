package de.kisner.otrcast.factory.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Movie;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;
import de.kisner.otrcast.model.xml.rss.Title;

public class XmlTitleFactory<MOVIE extends Movie<IMAGE,STORAGE>,
							SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE>,
							SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
							EPISODE extends Episode<SEASON>,
							IMAGE extends Image,
							STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlTitleFactory.class);

	public Title build(SEASON season)
    {
		return build(season.getSeries().getName()+" ("+season.getNr()+")");
    }
	
	public Title build(EPISODE episode)
    {
		return build(episode.getNr()+". "+episode.getName());
    }
	
    public static Title build(de.kisner.otrcast.model.xml.series.Episode episode)
    {
        return build(episode.getName());
    }

	public static Title build(String title)
	{
		Title xml = new Title();
		xml.setValue(title);
		return xml;
	}
}
