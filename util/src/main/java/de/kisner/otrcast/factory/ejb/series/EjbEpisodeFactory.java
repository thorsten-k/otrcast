package de.kisner.otrcast.factory.ejb.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;

public class EjbEpisodeFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,
								SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,
								EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,
								COVER extends Image,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(EjbEpisodeFactory.class);
	
	final Class<EPISODE> clEpisode;
	
	public EjbEpisodeFactory(final Class<EPISODE> clEpisode)
	{
		this.clEpisode=clEpisode;
	}
	 
	public static <SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,COVER extends Image,STORAGE extends Storage>
		EjbEpisodeFactory<SERIES,SEASON,EPISODE,COVER,STORAGE> factory(final Class<EPISODE> clEpisode)
	{
		return new EjbEpisodeFactory<SERIES,SEASON,EPISODE,COVER,STORAGE>(clEpisode);
	}
	
	public EPISODE build(SEASON season, de.kisner.otrcast.model.xml.series.Episode xml)
	{
		EPISODE ejb = null;
		
		try{ejb = clEpisode.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		ejb.setSeason(season);
		ejb.setName(xml.getName());
		ejb.setNr(xml.getNr());
		
		return ejb;
	}
}