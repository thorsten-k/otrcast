package de.kisner.otrcast.factory.xml.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;
import de.kisner.otrcast.model.xml.otr.Query;

public class XmlSeriesFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,COVER extends Image,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlSeriesFactory.class);
	
	private de.kisner.otrcast.model.xml.series.Series q;
	
	public XmlSeriesFactory(Query query){this(query.getSeries());}
	public XmlSeriesFactory(de.kisner.otrcast.model.xml.series.Series q){this.q=q;}
	
	public de.kisner.otrcast.model.xml.series.Series create2(String format)
	{
		de.kisner.otrcast.model.xml.series.Series xml = new de.kisner.otrcast.model.xml.series.Series();
		
		return xml;
	}
	
	public de.kisner.otrcast.model.xml.series.Series build(Series<SERIES,SEASON,EPISODE,COVER,STORAGE> ejb)
	{
		logger.trace(ejb.toString());
		de.kisner.otrcast.model.xml.series.Series xml = new de.kisner.otrcast.model.xml.series.Series();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		if(q.isSetKey()){xml.setKey(ejb.getCode());}
		
		if(q.isSetSeason())
		{
			XmlSeasonFactory<SERIES,SEASON,EPISODE,COVER,STORAGE> f = new XmlSeasonFactory<SERIES,SEASON,EPISODE,COVER,STORAGE>(q.getSeason().get(0));
			for(Season<SERIES,SEASON,EPISODE,COVER,STORAGE> season : ejb.getSeasons())
			{
				xml.getSeason().add(f.build(season));
			}
		}
		
		return xml;
	}
	
	public static de.kisner.otrcast.model.xml.series.Series build(String name)
	{
		de.kisner.otrcast.model.xml.series.Series xml = new de.kisner.otrcast.model.xml.series.Series();
		xml.setName(name);
		return xml;
	}	
}