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
import net.sf.otrcutmp4.model.xml.rss.Enclosure;

public class XmlEnclosureFactory<MOVIE extends Movie<IMAGE,STORAGE>,
								SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
								SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
								EPISODE extends Episode<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
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
