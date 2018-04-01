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
import de.kisner.otrcast.model.xml.rss.Enclosure;

public class XmlEnclosureFactory<MOVIE extends Movie<IMAGE,STORAGE>,
								SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE>,
								SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
								EPISODE extends Episode<SEASON>,
								IMAGE extends Image,
								STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlEnclosureFactory.class);
	
	private final UrlGenerator urlGenerator;
	
	public XmlEnclosureFactory(UrlGenerator urlGenerator)
	{
		this.urlGenerator=urlGenerator;
	}
	
	public Enclosure build(STORAGE storage)
	{		
		return build(storage.getId(),storage.getSize(),"video/mp4");
	}

	protected Enclosure build(long id, long length, String type)
	{
		Enclosure xml = new Enclosure();
		xml.setUrl(urlGenerator.enclosure(id));
		xml.setLength(length);
		xml.setType(type);
		return xml;
	}
}
