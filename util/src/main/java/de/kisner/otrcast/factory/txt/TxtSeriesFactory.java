package de.kisner.otrcast.factory.txt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.video.tv.Episode;
import de.kisner.otrcast.model.xml.video.tv.Series;

public class TxtSeriesFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtSeriesFactory.class);
	
	public static String build(Episode episode){return build(episode.getSeason().getSeries());}
	
	public static String build(Series series){return build(series,false);}
	public static String build(Series series, boolean withId)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(series.getName());
		if(withId && series.isSetId()){sb.append(" id:").append(series.getId());}
		
		return sb.toString();
	}
}
