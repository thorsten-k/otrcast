package de.kisner.otrcast.factory.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Movie;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;
import de.kisner.otrcast.interfaces.web.UrlGenerator;
import de.kisner.otrcast.model.xml.rss.Channel;

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
