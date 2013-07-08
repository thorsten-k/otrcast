package net.sf.otrcutmp4.factory.ejb.series;

import net.sf.otrcutmp4.interfaces.model.Cover;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSeasonFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER>,COVER extends Cover>
{	
	final static Logger logger = LoggerFactory.getLogger(EjbSeasonFactory.class);
	
	final Class<SEASON> clSeason;
	
	public EjbSeasonFactory(final Class<SEASON> clSeason)
	{
		this.clSeason=clSeason;
	}
	 
	public static <SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER>,COVER extends Cover>
		EjbSeasonFactory<SERIES,SEASON,EPISODE,COVER> factory(final Class<SEASON> clSeason)
	{
		return new EjbSeasonFactory<SERIES,SEASON,EPISODE,COVER>(clSeason);
	}
	
	public SEASON build(SERIES series, net.sf.otrcutmp4.model.xml.series.Season xml)
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