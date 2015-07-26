package net.sf.otrcutmp4.factory.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Image;
import net.sf.otrcutmp4.interfaces.model.Movie;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.interfaces.model.Storage;
import net.sf.otrcutmp4.interfaces.web.UrlGenerator;
import net.sf.otrcutmp4.model.xml.rss.Channel;

public class XmlChannelFactory<MOVIE extends Movie<IMAGE,STORAGE>,
								SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
								SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
								EPISODE extends Episode<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
								IMAGE extends Image,
								STORAGE extends Storage>
{
	final static Logger logger = LoggerFactory.getLogger(XmlChannelFactory.class);
	
	private XmlTitleFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE> xfTitle;
	private XmlItemFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE> xfItem;
	
	public XmlChannelFactory(UrlGenerator urlGenerator)
	{
		xfTitle = new XmlTitleFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE>();
		xfItem = new XmlItemFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE>(urlGenerator);
	}
	
	public Channel build(SEASON season)
	{
		Channel xml = new Channel();
		xml.setTitle(xfTitle.build(season));
		
		for(EPISODE episode : season.getEpisodes())
		{
			xml.getItem().add(xfItem.build(episode));
		}
		
		return xml;
	}
}
