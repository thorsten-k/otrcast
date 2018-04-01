package de.kisner.otrcast.factory.xml.video.tv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.factory.xml.series.XmlSeasonFactory;
import de.kisner.otrcast.factory.xml.tvdb.XmlSyncFactory;
import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;
import de.kisner.otrcast.model.xml.otr.Query;

public class XmlSeriesFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,
							SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,
							EPISODE extends Episode<SEASON>,
							COVER extends Image,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlSeriesFactory.class);
	
	private de.kisner.otrcast.model.xml.series.Series q;
	
	private XmlSeasonFactory<SERIES,SEASON,EPISODE,COVER,STORAGE> xfSeason;
	
	public XmlSeriesFactory(Query query){this(query.getSeries());}
	public XmlSeriesFactory(de.kisner.otrcast.model.xml.series.Series q)
	{
		this.q=q;
		if(q.isSetSeason()){xfSeason = new XmlSeasonFactory<SERIES,SEASON,EPISODE,COVER,STORAGE>(q.getSeason().get(0));}
	}
	
	public de.kisner.otrcast.model.xml.series.Series create2(String format)
	{
		de.kisner.otrcast.model.xml.series.Series xml = new de.kisner.otrcast.model.xml.series.Series();
		
		return xml;
	}
	
	public de.kisner.otrcast.model.xml.series.Series build(Series<SERIES,SEASON,EPISODE,COVER> ejb)
	{
		logger.trace(ejb.toString());
		de.kisner.otrcast.model.xml.series.Series xml = new de.kisner.otrcast.model.xml.series.Series();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		if(q.isSetKey()){xml.setKey(ejb.getCode());}
		
		if(q.isSetSeason())
		{
			for(Season<SERIES,SEASON,EPISODE,COVER,STORAGE> season : ejb.getSeasons())
			{
				xml.getSeason().add(xfSeason.build(season));
			}
		}
		
		return xml;
	}
	
	public static de.kisner.otrcast.model.xml.series.Series build()
	{
		return new de.kisner.otrcast.model.xml.series.Series();
	}
	
	public static de.kisner.otrcast.model.xml.series.Series build(String name)
	{
		de.kisner.otrcast.model.xml.series.Series xml = build();
		xml.setName(name);
		return xml;
	}
	
	public static de.kisner.otrcast.model.xml.series.Series build(com.uwetrottmann.thetvdb.entities.Series json)
	{
		de.kisner.otrcast.model.xml.series.Series xml = build();
		xml.setSync(XmlSyncFactory.build(json.id));
		xml.setName(json.seriesName);
		return xml;
	}
}