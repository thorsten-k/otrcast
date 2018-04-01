package de.kisner.otrcast.factory.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.factory.xml.itunes.XmlImageFactory;
import de.kisner.otrcast.factory.xml.itunes.XmlSummaryFactory;
import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Movie;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;
import de.kisner.otrcast.interfaces.web.UrlGenerator;
import de.kisner.otrcast.model.xml.rss.Item;

public class XmlItemFactory<MOVIE extends Movie<IMAGE,STORAGE>,
							SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE>,
							SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
							EPISODE extends Episode<SEASON>,
							IMAGE extends Image,
							STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlItemFactory.class);
		
	private XmlTitleFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE> xfTitle;
	private XmlGuidFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE> xfGuid;
	private XmlEnclosureFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE> xfEnclosure;
	private XmlImageFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE> xfImage;
	private final XmlDescriptionFactory<EPISODE> xfDescription;
	
	public XmlItemFactory(UrlGenerator urlGenerator)
	{
		xfTitle = new XmlTitleFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE>();
		xfGuid = new XmlGuidFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE>();
		xfEnclosure = new XmlEnclosureFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE>(urlGenerator);
		xfImage = new XmlImageFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE>(urlGenerator);
		xfDescription = new XmlDescriptionFactory<EPISODE>();
	}
	
	public Item build(EPISODE episode)
	{
		Item xml = new Item();
		xml.setTitle(xfTitle.build(episode));
		xml.setImage(xfImage.build(episode));
		xml.setDescription(xfDescription.build(episode));
		xml.setSummary(XmlSummaryFactory.build(episode.getNr()+""));
		xml.setGuid(xfGuid.build(episode));
		
		logger.warn("Enclosure and PubDate not set");
//		xml.setEnclosure(xfEnclosure.build(episode.getStorage()));
//		xml.setPubDate(XmlPubDateFactory.build(episode.getStorage()));
		return xml;
	}
	
	public static <IMAGE extends Image,STORAGE extends Storage>
		Item build(Movie<IMAGE,STORAGE> movie)
	{
		Item xml = new Item();
		xml.setTitle(XmlTitleFactory.build(movie.getName()));
		xml.setDescription(XmlDescriptionFactory.build(movie.getYear()+""));
		xml.setSummary(XmlSummaryFactory.build(movie.getYear()+" itunes"));
		xml.setPubDate(XmlPubDateFactory.build(movie.getStorage().getRecord()));
//		xml.setImage(XmlImageFactory.build("http://localhost:8080/erp/image/mcMovie/1.png"));
//		xml.setGuid(XmlGuidFactory.build(movie.getStorage()));
//		xml.setEnclosure(xfEnclosure.build(movie.getStorage()));
		return xml;
	}
}