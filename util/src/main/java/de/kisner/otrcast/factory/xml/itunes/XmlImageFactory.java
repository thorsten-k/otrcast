package de.kisner.otrcast.factory.xml.itunes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Movie;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;
import de.kisner.otrcast.interfaces.web.UrlGenerator;

public class XmlImageFactory<MOVIE extends Movie<IMAGE,STORAGE>,
						SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE>,
						SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
						EPISODE extends Episode<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
						IMAGE extends Image,
						STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlImageFactory.class);
	
	private UrlGenerator urlGenerator;
	
	public XmlImageFactory(UrlGenerator urlGenerator)
	{
		this.urlGenerator=urlGenerator;
	}
	
	public de.kisner.otrcast.model.xml.itunes.Image build(EPISODE episode)
	{
		de.kisner.otrcast.model.xml.itunes.Image xml = null;
		if(episode.getSeason()!=null && episode.getSeason().getCover()!=null)
		{
			IMAGE image = episode.getSeason().getCover();
			
			xml = new de.kisner.otrcast.model.xml.itunes.Image();
			xml.setHref(urlGenerator.image(image.getId(),image.getFileType()));
		}
		return xml;
	}
}
