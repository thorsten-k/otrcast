package net.sf.otrcutmp4.factory.xml.rss;

import net.sf.otrcutmp4.factory.xml.itunes.XmlImageFactory;
import net.sf.otrcutmp4.factory.xml.itunes.XmlSummaryFactory;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Movie;
import net.sf.otrcutmp4.model.xml.rss.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlItemFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlItemFactory.class);
	
	public static Item build(Episode episode)
	{
		Item xml = new Item();
		xml.setTitle(XmlTitleFactory.build(episode.getName()));
		xml.setDescription(XmlDescriptionFactory.build(episode));
		xml.setSummary(XmlSummaryFactory.build(episode.getNr()+""));
		xml.setEnclosure(XmlEnclosureFactory.build(episode.getStorage()));
		xml.setGuid(XmlGuidFactory.build(episode.getStorage()));
		xml.setPubDate(XmlPubDateFactory.build(episode.getStorage()));
		return xml;
	}
	
	public static Item build(Movie movie)
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