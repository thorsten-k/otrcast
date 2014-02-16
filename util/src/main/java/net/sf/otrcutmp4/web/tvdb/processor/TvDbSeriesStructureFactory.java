package net.sf.otrcutmp4.web.tvdb.processor;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.factory.xml.series.XmlSeasonFactory;
import net.sf.otrcutmp4.factory.xml.tvdb.XmlSyncFactory;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TvDbSeriesStructureFactory
{
	final static Logger logger = LoggerFactory.getLogger(TvDbSeriesStructureFactory.class);

	private Document doc;
	
	public TvDbSeriesStructureFactory(Document doc)
	{
        this.doc=doc;
	}
	
	public void debugDocument()
	{
		XMLOutputter serializer= new XMLOutputter(Format.getPrettyFormat());
		logger.info(serializer.outputString(doc));
	}
	
	public Series build()
	{
		Series series = new Series();
	
		XPathFactory xPathFactory = XPathFactory.instance();
        XPathExpression<Element> xPathExpression = xPathFactory.compile("/Data/Episode", Filters.element());
        for(Element e : xPathExpression.evaluate(doc))
        {
        	handleEpisode(e);
        	break;
        }
		return series;
	}
	
	private void handleEpisode(Element e)
	{
		Episode episode = buildEpisode(e);
		episode.setSeason(buildSeason(e));
	
		JaxbUtil.info(episode);
		JDomUtil.debug(e);
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