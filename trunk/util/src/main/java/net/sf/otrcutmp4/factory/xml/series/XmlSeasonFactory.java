package net.sf.otrcutmp4.factory.xml.series;

import net.sf.otrcutmp4.factory.xml.mc.XmlCoverFactory;
import net.sf.otrcutmp4.interfaces.model.Image;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.interfaces.model.Storage;
import net.sf.otrcutmp4.model.xml.otr.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSeasonFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,COVER extends Image,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlSeasonFactory.class);
	
	private net.sf.otrcutmp4.model.xml.series.Season q;
	
	public XmlSeasonFactory(Query query){this(query.getSeason());}
	public XmlSeasonFactory(net.sf.otrcutmp4.model.xml.series.Season q){this.q=q;}
	
	public net.sf.otrcutmp4.model.xml.series.Season build(Season<SERIES,SEASON,EPISODE,COVER,STORAGE> ejb)
	{
		logger.trace("\t"+ejb.toString());
		net.sf.otrcutmp4.model.xml.series.Season xml = new net.sf.otrcutmp4.model.xml.series.Season();
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
		
		if(q.isSetCover() && ejb.getCover()!=null)
		{
			XmlCoverFactory f = new XmlCoverFactory(q.getCover());
			xml.setCover(f.build(ejb.getCover()));
		}
		
		return xml;
	}
}