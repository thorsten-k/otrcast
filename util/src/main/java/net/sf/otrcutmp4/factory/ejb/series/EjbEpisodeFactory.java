package net.sf.otrcutmp4.factory.ejb.series;

import net.sf.otrcutmp4.interfaces.model.Image;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.interfaces.model.Storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbEpisodeFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,COVER extends Image,STORAGE extends Storage>
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