package de.kisner.otrcast.factory.xml.video.tv;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.factory.txt.TxtEpisodeFactory;
import de.kisner.otrcast.factory.xml.series.XmlSeasonFactory;
import de.kisner.otrcast.factory.xml.tvdb.XmlSyncFactory;
import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;
import de.kisner.otrcast.model.xml.otr.Query;

public class XmlEpisodeFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,
								SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,
								EPISODE extends Episode<SEASON>,
								COVER extends Image,
								STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlEpisodeFactory.class);
	
	private final de.kisner.otrcast.model.xml.video.tv.Episode q;
	
	private XmlSeasonFactory<SERIES,SEASON,EPISODE,COVER,STORAGE> xfSeason;

	public XmlEpisodeFactory(Query query){this(query.getEpisode());}
	public XmlEpisodeFactory(de.kisner.otrcast.model.xml.video.tv.Episode q)
	{
		this.q=q;
		if(q.isSetSeason()){xfSeason = new XmlSeasonFactory<SERIES,SEASON,EPISODE,COVER,STORAGE>(q.getSeason());}
		
	}
	
	public de.kisner.otrcast.model.xml.video.tv.Episode build(Episode<SEASON> ejb)
	{
		logger.trace("\t\t"+ejb.toString());
		de.kisner.otrcast.model.xml.video.tv.Episode xml = new de.kisner.otrcast.model.xml.video.tv.Episode();
		if(Objects.nonNull(q.getId())) {xml.setId(ejb.getId());}
		if(q.isSetNr()){xml.setNr(ejb.getNr());}
		if(q.isSetName()){xml.setName(TxtEpisodeFactory.buld(ejb.getName()));}
		if(q.isSetSeason()){xml.setSeason(xfSeason.build(ejb.getSeason()));}
		
		return xml;
	}
	
	public static de.kisner.otrcast.model.xml.video.tv.Episode build(String seriesName, int seasonNr, int nr, String name)
	{
		de.kisner.otrcast.model.xml.video.tv.Series series = new de.kisner.otrcast.model.xml.video.tv.Series();
		series.setName(seriesName);
		
		de.kisner.otrcast.model.xml.video.tv.Season season = new de.kisner.otrcast.model.xml.video.tv.Season();
		season.setNr(seasonNr);
		season.setSeries(series);
		
		de.kisner.otrcast.model.xml.video.tv.Episode xml = build();
		xml.setName(name);
		xml.setNr(nr);
		xml.setSeason(season);
		
		return xml;
	}
	
	public static de.kisner.otrcast.model.xml.video.tv.Episode build(long nr, String name)
	{		
		de.kisner.otrcast.model.xml.video.tv.Episode xml = build();
		xml.setName(TxtEpisodeFactory.buld(name));
		xml.setNr(nr);
		return xml;
	}
	
	public static de.kisner.otrcast.model.xml.video.tv.Episode build()
	{		
		return new de.kisner.otrcast.model.xml.video.tv.Episode();
	}
	
	public static de.kisner.otrcast.model.xml.video.tv.Episode build(com.uwetrottmann.thetvdb.entities.Episode json)
	{
		de.kisner.otrcast.model.xml.video.tv.Episode xml = build();
		xml.setSync(XmlSyncFactory.build(json.id));
		xml.setName(json.episodeName);
		xml.setNr(json.airedEpisodeNumber);
		return xml;
	}
}