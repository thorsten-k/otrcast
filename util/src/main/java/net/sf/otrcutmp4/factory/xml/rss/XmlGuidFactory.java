package net.sf.otrcutmp4.factory.xml.rss;

import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Image;
import net.sf.otrcutmp4.interfaces.model.Movie;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.interfaces.model.Storage;
import net.sf.otrcutmp4.model.xml.rss.Guid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlGuidFactory<MOVIE extends Movie<IMAGE,STORAGE>,
							SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
							SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
							EPISODE extends Episode<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
							IMAGE extends Image,
							STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlGuidFactory.class);
	
	public Guid build(EPISODE episode)
	{
		return build(false,""+episode.getId()+"x");
	}
		
	public static Guid build(boolean isPermaLink, String url)
	{
		Guid xml = new Guid();
		xml.setIsPermaLink(isPermaLink);
		xml.setValue(url);
		return xml;
	}
}