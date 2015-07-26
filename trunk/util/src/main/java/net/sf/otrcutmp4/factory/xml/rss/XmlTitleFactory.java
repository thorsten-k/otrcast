package net.sf.otrcutmp4.factory.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Image;
import net.sf.otrcutmp4.interfaces.model.Movie;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.interfaces.model.Storage;
import net.sf.otrcutmp4.model.xml.rss.Title;

public class XmlTitleFactory<MOVIE extends Movie<IMAGE,STORAGE>,
							SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
							SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
							EPISODE extends Episode<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
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
	
    public static Title build(net.sf.otrcutmp4.model.xml.series.Episode episode)
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
