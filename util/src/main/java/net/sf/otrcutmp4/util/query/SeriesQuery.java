package net.sf.otrcutmp4.util.query;

import java.util.Hashtable;
import java.util.Map;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.otr.Query;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;

public class SeriesQuery
{
	public static enum Key {Series,SeriesWithSeason}
	
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
			}
			mQueries.put(key, q);
			JaxbUtil.info(q);
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
	
	public static Episode episodeInfo()
	{
		Series series = new Series();
		series.setName("");
		series.setKey("");
		
		Season season = new Season();
		season.setNr(0);
		season.setName("");
		season.setSeries(series);
		
		Episode episode = new Episode();
		episode.setId(0);
		episode.setNr(0);
		episode.setName("");
		episode.setSeason(season);
		
    	return episode;
	}
	
	
}
