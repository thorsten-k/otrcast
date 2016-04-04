package de.kisner.otrcast.factory.xml.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;
import de.kisner.otrcast.model.xml.otr.Query;

public class XmlEpisodeFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,
								SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,
								EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,
								COVER extends Image,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlEpisodeFactory.class);
	
	private de.kisner.otrcast.model.xml.series.Episode q;
	
	public XmlEpisodeFactory(Query query){this(query.getEpisode());}
	public XmlEpisodeFactory(de.kisner.otrcast.model.xml.series.Episode q){this.q=q;}
	
	public de.kisner.otrcast.model.xml.series.Episode build(Episode<SERIES,SEASON,EPISODE,COVER,STORAGE> ejb)
	{
		logger.trace("\t\t"+ejb.toString());
		de.kisner.otrcast.model.xml.series.Episode xml = new de.kisner.otrcast.model.xml.series.Episode();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetNr()){xml.setNr(ejb.getNr());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		
		if(q.isSetSeason())
		{
			XmlSeasonFactory<SERIES,SEASON,EPISODE,COVER,STORAGE> f = new XmlSeasonFactory<SERIES,SEASON,EPISODE,COVER,STORAGE>(q.getSeason());
			xml.setSeason(f.build(ejb.getSeason()));
		}
		
		return xml;
	}
	
	public static de.kisner.otrcast.model.xml.series.Episode build(String seriesName, int seasonNr, int nr, String name)
	{
		de.kisner.otrcast.model.xml.series.Series series = new de.kisner.otrcast.model.xml.series.Series();
		series.setName(seriesName);
		
		de.kisner.otrcast.model.xml.series.Season season = new de.kisner.otrcast.model.xml.series.Season();
		season.setNr(seasonNr);
		season.setSeries(series);
		
		de.kisner.otrcast.model.xml.series.Episode xml = build();
		xml.setName(name);
		xml.setNr(nr);
		xml.setSeason(season);
		
		return xml;
	}
	
	public static de.kisner.otrcast.model.xml.series.Episode build(int nr, String name)
	{		
		de.kisner.otrcast.model.xml.series.Episode xml = build();
		xml.setName(name);
		xml.setNr(nr);
		
		return xml;
	}
	
	public static de.kisner.otrcast.model.xml.series.Episode build()
	{		
		return new de.kisner.otrcast.model.xml.series.Episode();
	}
}