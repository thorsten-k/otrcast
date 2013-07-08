package net.sf.otrcutmp4.factory.ejb.series;

import net.sf.otrcutmp4.interfaces.model.Cover;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.interfaces.model.Storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSeriesFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,COVER extends Cover,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(EjbSeriesFactory.class);
	
	final Class<SERIES> clSeries;
	
	public EjbSeriesFactory(final Class<SERIES> clSeries)
	{
		this.clSeries=clSeries;
	}
	 
	public static <SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,COVER extends Cover,STORAGE extends Storage>
		EjbSeriesFactory<SERIES,SEASON,EPISODE,COVER,STORAGE> factory(final Class<SERIES> clSeries)
	{
		return new EjbSeriesFactory<SERIES,SEASON,EPISODE,COVER,STORAGE>(clSeries);
	}
	
	public SERIES build(net.sf.otrcutmp4.model.xml.series.Series xml)
	{
		SERIES ejb = null;
		
		try{ejb = clSeries.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		ejb.setName(xml.getName());
		
		return ejb;
	}
}