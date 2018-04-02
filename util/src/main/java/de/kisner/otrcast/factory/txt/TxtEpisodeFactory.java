package de.kisner.otrcast.factory.txt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.video.tv.Episode;

public class TxtEpisodeFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtEpisodeFactory.class);
	
	public static String dotPattern = "(.*)(\\w\\.\\.\\.)(.*)";
	public static String dots = "...";
	
	private static Pattern p = Pattern.compile(TxtEpisodeFactory.dotPattern);
	
	public static String build(Episode episode){return build(episode,false);}
	public static String build(Episode episode, boolean withId)
	{
		StringBuffer sb = new StringBuffer();
		
		if(episode.isSetSeason() && episode.getSeason().isSetSeries())
		{
			sb.append(episode.getSeason().getSeries().getName());
			sb.append(" ").append(episode.getSeason().getNr());
			sb.append("x").append(episode.getNr());
			sb.append(" ").append(episode.getName());
			
		}
		if(withId && episode.isSetId()){sb.append(" otrc:").append(episode.getId());}
		
		return sb.toString();
	}
	
	public static String buld(String name)
	{
		Matcher m = p.matcher(name);
		if(!m.matches()){return name;}
		else
		{
			logger.info("Start: "+m.start(2));
			StringBuffer sb = new StringBuffer();
			sb.append(name.substring(0, m.start(2)+1));
			sb.append(" ...");
			sb.append(name.substring(m.end(2),name.length()));
			return buld(sb.toString());
			
			
		}
	}
}
