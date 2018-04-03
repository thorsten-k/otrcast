package de.kisner.otrcast.factory.ejb.tv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Movie;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;

public class EjbEpisodeFactory<MOVIE extends Movie<COVER,STORAGE>,
								SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,
								SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,
								EPISODE extends Episode<SEASON>,
								COVER extends Image,
								STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(EjbEpisodeFactory.class);
	
	final Class<EPISODE> cEpisode;
	
	public EjbEpisodeFactory(final Class<EPISODE> cEpisode)
	{
		this.cEpisode=cEpisode;
	}
	
	public EPISODE build(SEASON season, de.kisner.otrcast.model.xml.video.tv.Episode xml)
	{
		EPISODE ejb = null;
		
		try{ejb = cEpisode.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		ejb.setSeason(season);
		ejb.setName(xml.getName());
		ejb.setNr(xml.getNr());
		
		return ejb;
	}
	
//	public Map<SERIES,List<EPISODE>> toMapSeries(Set<EPISODE> set){return toMapSeries(new ArrayList<>(set);)
	public Map<SERIES,List<EPISODE>> toMapSeries(Collection<EPISODE> list)
	{
		Map<SERIES,List<EPISODE>> map = new HashMap<SERIES,List<EPISODE>>();
		
		for(EPISODE episode : list)
		{
			if(!map.containsKey(episode.getSeason().getSeries())) {map.put(episode.getSeason().getSeries(), new ArrayList<EPISODE>());}
			map.get(episode.getSeason().getSeries()).add(episode);
		}
		
		return map;
	}
	
	public Map<SERIES,Map<SEASON,List<EPISODE>>> toMapSeriesSeason(Collection<EPISODE> list)
	{
		Map<SERIES,Map<SEASON,List<EPISODE>>> map = new HashMap<SERIES,Map<SEASON,List<EPISODE>>>();
		
		for(EPISODE episode : list)
		{
			SERIES series = episode.getSeason().getSeries();
			SEASON season = episode.getSeason();
			if(!map.containsKey(series)) {map.put(series, new HashMap<SEASON,List<EPISODE>>());}
			if(!map.get(series).containsKey(season)){map.get(series).put(season, new ArrayList<EPISODE>());}
			map.get(series).get(season).add(episode);
		}
		
		return map;
	}
}