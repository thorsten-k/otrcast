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

import de.kisner.otrcast.controller.xpath.SeriesXpath;
import de.kisner.otrcast.factory.xml.series.XmlSeasonFactory;
import de.kisner.otrcast.factory.xml.tvdb.XmlSyncFactory;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Season;
import de.kisner.otrcast.model.xml.series.Series;

public class TvDbSeriesStructureFactory
{
	final static Logger logger = LoggerFactory.getLogger(TvDbSeriesStructureFactory.class);

	private Document doc;
	private Series series;
	
	public TvDbSeriesStructureFactory(Document doc)
	{
        this.doc=doc;
        series = new Series();
	}
	
	public void debugDocument()
	{
		XMLOutputter serializer= new XMLOutputter(Format.getPrettyFormat());
		logger.info(serializer.outputString(doc));
	}
	
	public Series build()
	{
		XPathFactory xPathFactory = XPathFactory.instance();
        XPathExpression<Element> xPathExpression = xPathFactory.compile("/Data/Episode", Filters.element());
        for(Element e : xPathExpression.evaluate(doc))
        {
        	handleEpisode(e);
     //   	break;
        }
		return series;
	}
	
	private void handleEpisode(Element element)
	{
		Episode episode = buildEpisode(element);
		episode.setSeason(buildSeason(element));
	
		Season season = null;
		try
		{
			season = SeriesXpath.getSeason(series, episode.getSeason().getNr());
		}
		catch (ExlpXpathNotFoundException e1)
		{
			season = episode.getSeason();
			series.getSeason().add(episode.getSeason());
		}
		catch (ExlpXpathNotUniqueException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		episode.setSeason(null);
		season.getEpisode().add(episode);
		
		
//		JaxbUtil.info(episode);
//		JDomUtil.debug(element);
	}
	
	private Season buildSeason(Element e)
	{
		Season season = XmlSeasonFactory.build(new Integer(e.getChild("SeasonNumber").getValue()));
		season.setSync(XmlSyncFactory.build(new Long(e.getChild("seasonid").getValue())));
		return season;
	}
	
	private Episode buildEpisode(Element e)
	{
		Episode episode = new Episode();
		episode.setName(e.getChild("EpisodeName").getValue());
		episode.setNr(new Long(e.getChild("EpisodeNumber").getValue()));
		
		episode.setSync(XmlSyncFactory.build(new Long(e.getChild("id").getValue())));
		return episode;
	}
}