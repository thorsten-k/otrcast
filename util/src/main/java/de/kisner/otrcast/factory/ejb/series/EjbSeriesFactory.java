package de.kisner.otrcast.factory.ejb.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;

public class EjbSeriesFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,
								SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,
								EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,
								COVER extends Image,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(EjbSeriesFactory.class);
	
	final Class<SERIES> clSeries;
	
	public EjbSeriesFactory(final Class<SERIES> clSeries)
	{
		this.clSeries=clSeries;
	}
	 
	public static <SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,
					SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,
					EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,
					COVER extends Image,STORAGE extends Storage>
		EjbSeriesFactory<SERIES,SEASON,EPISODE,COVER,STORAGE> factory(final Class<SERIES> clSeries)
	{
		return new EjbSeriesFactory<SERIES,SEASON,EPISODE,COVER,STORAGE>(clSeries);
	}
	
	public SERIES build(de.kisner.otrcast.model.xml.series.Series xml)
	{
		SERIES ejb = null;
		
		try{ejb = clSeries.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		ejb.setName(xml.getName());
		
		return ejb;
	}
}