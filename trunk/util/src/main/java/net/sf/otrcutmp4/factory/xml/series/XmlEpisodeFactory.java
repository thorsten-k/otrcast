package net.sf.otrcutmp4.factory.xml.series;

import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.model.xml.otr.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlEpisodeFactory<SERIES extends Series<SERIES,SEASON,EPISODE>, SEASON extends Season<SERIES,SEASON,EPISODE>,EPISODE extends Episode<SERIES,SEASON,EPISODE>>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlEpisodeFactory.class);
	
	private net.sf.otrcutmp4.model.xml.series.Episode q;
	
	public XmlEpisodeFactory(Query query){this(query.getEpisode());}
	public XmlEpisodeFactory(net.sf.otrcutmp4.model.xml.series.Episode q){this.q=q;}
	
	public net.sf.otrcutmp4.model.xml.series.Episode build(Episode<SERIES,SEASON,EPISODE> ejb)
	{
		net.sf.otrcutmp4.model.xml.series.Episode xml = new net.sf.otrcutmp4.model.xml.series.Episode();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetNr()){xml.setNr(ejb.getNr());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		
		if(q.isSetSeason())
		{
			XmlSeasonFactory<SERIES,SEASON,EPISODE> f = new XmlSeasonFactory<SERIES,SEASON,EPISODE>(q.getSeason());
			xml.setSeason(f.build(ejb.getSeason()));
		}
		
		return xml;
	}
	
	public static net.sf.otrcutmp4.model.xml.series.Episode build(String seriesName, int seasonNr, int nr, String name)
	{
		net.sf.otrcutmp4.model.xml.series.Series series = new net.sf.otrcutmp4.model.xml.series.Series();
		series.setName(seriesName);
		
		net.sf.otrcutmp4.model.xml.series.Season season = new net.sf.otrcutmp4.model.xml.series.Season();
		season.setNr(seasonNr);
		season.setSeries(series);
		
		net.sf.otrcutmp4.model.xml.series.Episode xml = new net.sf.otrcutmp4.model.xml.series.Episode();
		xml.setName(name);
		xml.setNr(nr);
		xml.setSeason(season);
		
		return xml;
	}
}
