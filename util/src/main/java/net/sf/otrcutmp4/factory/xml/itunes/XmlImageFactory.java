package net.sf.otrcutmp4.factory.xml.itunes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Image;
import net.sf.otrcutmp4.interfaces.model.Movie;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.interfaces.model.Storage;
import net.sf.otrcutmp4.interfaces.web.UrlGenerator;

public class XmlImageFactory<MOVIE extends Movie<IMAGE,STORAGE>,
						SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
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
	
	public net.sf.otrcutmp4.model.xml.itunes.Image build(EPISODE episode)
	{
		net.sf.otrcutmp4.model.xml.itunes.Image xml = null;
		if(episode.getSeason()!=null && episode.getSeason().getCover()!=null)
		{
			IMAGE image = episode.getSeason().getCover();
			
			xml = new net.sf.otrcutmp4.model.xml.itunes.Image();
			xml.setHref(urlGenerator.image(image.getId(),image.getFileType()));
		}
		return xml;
	}
}
