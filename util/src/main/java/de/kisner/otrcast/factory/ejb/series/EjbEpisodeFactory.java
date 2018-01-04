package de.kisner.otrcast.factory.ejb.series;

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
								EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,
								COVER extends Image,
								STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(EjbEpisodeFactory.class);
	
	final Class<EPISODE> cEpisode;
	
//	private EjbSeasonFactory<MOVIE,SERIES,SEASON,EPISODE,COVER,STORAGE> efSeason;
	
	public EjbEpisodeFactory(final Class<EPISODE> cEpisode)
	{
		this.cEpisode=cEpisode;
	}
	 
	public static <MOVIE extends Movie<COVER,STORAGE>,
					SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,
					SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,
					EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,
					COVER extends Image,STORAGE extends Storage>
		EjbEpisodeFactory<MOVIE,SERIES,SEASON,EPISODE,COVER,STORAGE> factory(final Class<EPISODE> cEpisode)
	{
		return new EjbEpisodeFactory<MOVIE,SERIES,SEASON,EPISODE,COVER,STORAGE>(cEpisode);
	}
	
	public EPISODE build(SEASON season, de.kisner.otrcast.model.xml.series.Episode xml)
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
}