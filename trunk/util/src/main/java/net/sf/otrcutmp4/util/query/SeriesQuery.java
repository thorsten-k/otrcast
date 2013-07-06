package net.sf.otrcutmp4.util.query;

import java.util.Hashtable;
import java.util.Map;

import net.sf.otrcutmp4.model.xml.mc.Cover;
import net.sf.otrcutmp4.model.xml.otr.Query;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeriesQuery
{
	final static Logger logger = LoggerFactory.getLogger(SeriesQuery.class);
	public static enum Key {SeriesAll,Series,SeriesWithSeason,SeasonWithEpisodes}
	
	private static Map<Key,Query> mQueries;
	
	public static Query get(Key key)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,Query>();}
		if(!mQueries.containsKey(key))
		{
			Query q = new Query();
			switch(key)
			{
				case Series: q.setSeries(series());break;
				case SeriesWithSeason: q.setSeries(seriesWithSeason());break;
				case SeasonWithEpisodes: q.setSeason(seasonWithEpisodes());break;
				case SeriesAll: q.setSeries(seriesAll());break;
			}
//			logger.info("Query for key: "+key);
//			JaxbUtil.info(q);
			mQueries.put(key, q);
		}
		
		return mQueries.get(key);
	}
	
	public static Series series()
	{
		Series xml = new Series();
		xml.setId(0);
		xml.setName("");
		return xml;
	}
	
	public static Series seriesAll()
	{
		Season season = season();
		season.setCover(cover());
		
		Series xml = series();
		xml.getSeason().add(season);
		xml.getSeason().get(0).getEpisode().add(episode());
		return xml;
	}
	
	public static Series seriesWithSeason()
	{
		Series xml = series();
		xml.getSeason().add(season());
		return xml;
	}
	
	public static Season season()
	{
		Season xml = new Season();
		xml.setId(0);
		xml.setNr(0);
		xml.setName("");
		return xml;
	}
	
	public static Season seasonWithEpisodes()
	{
		Season xml = season();
		xml.setSeries(series());
		xml.getEpisode().add(episode());
		return xml;
	}
	
	public static Episode episode()
	{
		Episode xml = new Episode();
		xml.setId(0);
		xml.setNr(0);
		xml.setName("");
		return xml;
	}
	
	public static Episode episodeWithSeasonandSeries()
	{
		Series series = new Series();
		series.setName("");
		series.setKey("");
		
		Season season = new Season();
		season.setNr(0);
		season.setName("");
		season.setSeries(series);
		
		Episode xml = episode();
		xml.setSeason(season);
		
    	return xml;
	}
	
	public static Cover cover()
	{
		Cover xml = new Cover();
		xml.setId(0);
		xml.setType("");
		xml.setData("x".getBytes());
		return xml;
	}
	
}