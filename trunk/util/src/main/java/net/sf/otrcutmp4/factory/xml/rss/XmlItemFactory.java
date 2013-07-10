package net.sf.otrcutmp4.factory.xml.rss;

import net.sf.otrcutmp4.factory.xml.itunes.XmlImageFactory;
import net.sf.otrcutmp4.factory.xml.itunes.XmlSummaryFactory;
import net.sf.otrcutmp4.interfaces.model.Cover;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Movie;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.interfaces.model.Storage;
import net.sf.otrcutmp4.model.xml.rss.Item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlItemFactory<MOVIE extends Movie<COVER,STORAGE>,SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,COVER extends Cover,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlItemFactory.class);
	
	public Item build(Episode<SERIES,SEASON,EPISODE,COVER,STORAGE> episode)
	{
		Item xml = new Item();
		xml.setTitle(XmlTitleFactory.build(episode.getName()));
		xml.setDescription(XmlDescriptionFactory.build(episode.getNr()+""));
		xml.setSummary(XmlSummaryFactory.build(episode.getNr()+""));
		xml.setEnclosure(XmlEnclosureFactory.build(episode.getStorage()));
		xml.setGuid(XmlGuidFactory.build(episode.getStorage()));
		xml.setPubDate(XmlPubDateFactory.build(episode.getStorage().getRecord()));
		return xml;
	}
	
	public Item build(Movie<COVER,STORAGE> movie)
	{
		Item xml = new Item();
		xml.setTitle(XmlTitleFactory.build(movie.getName()));
		xml.setDescription(XmlDescriptionFactory.build(movie.getYear()+""));
		xml.setSummary(XmlSummaryFactory.build(movie.getYear()+" itunes"));
		xml.setPubDate(XmlPubDateFactory.build(movie.getStorage().getRecord()));
		xml.setImage(XmlImageFactory.build("http://localhost:8080/erp/image/mcMovie/1.png"));
		xml.setGuid(XmlGuidFactory.build(movie.getStorage()));
		xml.setEnclosure(XmlEnclosureFactory.build(movie.getStorage()));
		return xml;
	}
}