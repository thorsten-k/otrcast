package net.sf.otrcutmp4.controller.factory.xml.series;

import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlEpisodeFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlEpisodeFactory.class);
		
	public static Episode create(String seriesName, int seasonNr, int nr, String name)
	{
		Series series = new Series();
		series.setName(seriesName);
		
		Season season = new Season();
		season.setNr(seasonNr);
		season.setSeries(series);
		
		Episode xml = new Episode();
		xml.setName(name);
		xml.setNr(nr);
		xml.setSeason(season);
		
		return xml;
	}
}
