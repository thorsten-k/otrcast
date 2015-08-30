package de.kisner.otrcast.web.tvdb.processor;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.factory.xml.series.XmlSeasonFactory;
import de.kisner.otrcast.model.xml.series.Season;
import de.kisner.otrcast.model.xml.series.Series;
import de.kisner.otrcast.model.xml.tvdb.Banner;
import de.kisner.otrcast.model.xml.tvdb.Banners;
import de.kisner.otrcast.util.query.xpath.SeriesXpath;

public class TvDbBannerFactory
{
	final static Logger logger = LoggerFactory.getLogger(TvDbBannerFactory.class);
	public static enum Type {series,season,poster,fanart}
	
	private Document doc;
	private Banners banners;
	
	private Series series;

	public TvDbBannerFactory(Document doc)
	{
        this.doc=doc;
        banners = new Banners();
        series = new Series();
	}
	
	public void debugDocument()
	{
		XMLOutputter serializer= new XMLOutputter(Format.getPrettyFormat());
		logger.info(serializer.outputString(doc));
	}
	
	public Banners build()
	{
		XPathFactory xPathFactory = XPathFactory.instance();
        XPathExpression<Element> xPathExpression = xPathFactory.compile("/Banners/Banner", Filters.element());
        for(Element e : xPathExpression.evaluate(doc))
        {
        	handleBanner(e);
        }

        banners.getSeason().addAll(series.getSeason());
		return banners;
	}
	
	private void handleBanner(Element element)
	{
		Banner banner = new Banner();
		banner.setUrl(element.getChild("BannerPath").getValue());
		banner.setType(element.getChild("BannerType").getValue());
		
		Type type = Type.valueOf(banner.getType());
		switch(type)
		{
			case season:	handleSeason(banner, element);break;
			default:		logger.trace("Unhandled type "+type);
		}
	}
	
	private void handleSeason(Banner banner, Element element)
	{
		banner.setType(null);
		long nr = new Long(element.getChild("Season").getValue());
		Season season = null;
		try
		{
			season = SeriesXpath.getSeason(series, nr);
		}
		catch (ExlpXpathNotFoundException e)
		{
			season = XmlSeasonFactory.build(nr);
			season.setBanners(new Banners());
			series.getSeason().add(season);
		}
		catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		season.getBanners().getBanner().add(banner);
	}
}