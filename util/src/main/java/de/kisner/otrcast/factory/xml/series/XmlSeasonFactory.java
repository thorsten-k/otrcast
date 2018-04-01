package de.kisner.otrcast.factory.xml.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.factory.xml.mc.XmlImageFactory;
import de.kisner.otrcast.factory.xml.video.tv.XmlEpisodeFactory;
import de.kisner.otrcast.factory.xml.video.tv.XmlSeriesFactory;
import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;
import de.kisner.otrcast.model.xml.otr.Query;

public class XmlSeasonFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,
							SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,
							EPISODE extends Episode<SEASON>,
							COVER extends Image,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlSeasonFactory.class);
	
	private de.kisner.otrcast.model.xml.series.Season q;
	
	public XmlSeasonFactory(Query query){this(query.getSeason());}
	public XmlSeasonFactory(de.kisner.otrcast.model.xml.series.Season q){this.q=q;}
	
	public de.kisner.otrcast.model.xml.series.Season build(Season<SERIES,SEASON,EPISODE,COVER,STORAGE> ejb)
	{
		logger.trace("\t"+ejb.toString());
		de.kisner.otrcast.model.xml.series.Season xml = new de.kisner.otrcast.model.xml.series.Season();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetNr()){xml.setNr(ejb.getNr());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		
		if(q.isSetSeries())
		{
			XmlSeriesFactory<SERIES,SEASON,EPISODE,COVER,STORAGE> f = new XmlSeriesFactory<SERIES,SEASON,EPISODE,COVER,STORAGE>(q.getSeries());
			xml.setSeries(f.build(ejb.getSeries()));
		}
		
		if(q.isSetEpisode())
		{
			XmlEpisodeFactory<SERIES,SEASON,EPISODE,COVER,STORAGE> f = new XmlEpisodeFactory<SERIES,SEASON,EPISODE,COVER,STORAGE>(q.getEpisode().get(0));
			for(EPISODE episode : ejb.getEpisodes())
			{
				xml.getEpisode().add(f.build(episode));
			}
		}
		
		if(q.isSetImage() && ejb.getCover()!=null)
		{
			XmlImageFactory f = new XmlImageFactory(q.getImage());
			xml.setImage(f.build(ejb.getCover()));
		}
		
		return xml;
	}
	
	public static de.kisner.otrcast.model.xml.series.Season build(long nr)
	{
		de.kisner.otrcast.model.xml.series.Season season = new de.kisner.otrcast.model.xml.series.Season();
		season.setNr(nr);
		return season;
	}
}