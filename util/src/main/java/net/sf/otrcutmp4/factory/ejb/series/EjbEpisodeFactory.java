package net.sf.otrcutmp4.factory.ejb.series;

import net.sf.otrcutmp4.interfaces.model.Cover;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbEpisodeFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER>,COVER extends Cover>
{	
	final static Logger logger = LoggerFactory.getLogger(EjbEpisodeFactory.class);
	
	final Class<EPISODE> clEpisode;
	
	public EjbEpisodeFactory(final Class<EPISODE> clEpisode)
	{
		this.clEpisode=clEpisode;
	}
	 
	public static <SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER>,COVER extends Cover>
		EjbEpisodeFactory<SERIES,SEASON,EPISODE,COVER> factory(final Class<EPISODE> clEpisode)
	{
		return new EjbEpisodeFactory<SERIES,SEASON,EPISODE,COVER>(clEpisode);
	}
	
	public EPISODE build(SEASON season, net.sf.otrcutmp4.model.xml.series.Episode xml)
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