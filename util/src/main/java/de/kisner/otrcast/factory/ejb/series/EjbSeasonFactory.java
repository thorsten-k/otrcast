package de.kisner.otrcast.factory.ejb.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;

public class EjbSeasonFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,COVER extends Image,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(EjbSeasonFactory.class);
	
	final Class<SEASON> clSeason;
	
	public EjbSeasonFactory(final Class<SEASON> clSeason)
	{
		this.clSeason=clSeason;
	}
	 
	public static <SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,COVER extends Image,STORAGE extends Storage>
		EjbSeasonFactory<SERIES,SEASON,EPISODE,COVER,STORAGE> factory(final Class<SEASON> clSeason)
	{
		return new EjbSeasonFactory<SERIES,SEASON,EPISODE,COVER,STORAGE>(clSeason);
	}
	
	public SEASON build(SERIES series, de.kisner.otrcast.model.xml.series.Season xml)
	{
		SEASON ejb = null;
		
		try{ejb = clSeason.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		ejb.setSeries(series);
		ejb.setName(xml.getName());
		ejb.setNr(xml.getNr());
		
		return ejb;
	}
}