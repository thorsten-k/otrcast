package net.sf.otrcutmp4.factory.ejb.series;

import net.sf.otrcutmp4.interfaces.model.Cover;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSeriesFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER>,COVER extends Cover>
{	
	final static Logger logger = LoggerFactory.getLogger(EjbSeriesFactory.class);
	
	final Class<SERIES> clSeries;
	
	public EjbSeriesFactory(final Class<SERIES> clSeries)
	{
		this.clSeries=clSeries;
	}
	 
	public static <SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER>,COVER extends Cover>
		EjbSeriesFactory<SERIES,SEASON,EPISODE,COVER> factory(final Class<SERIES> clSeries)
	{
		return new EjbSeriesFactory<SERIES,SEASON,EPISODE,COVER>(clSeries);
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