package net.sf.otrcutmp4.factory.xml.series;

import net.sf.otrcutmp4.interfaces.model.Cover;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.model.xml.otr.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSeriesFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER>,COVER extends Cover>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlSeriesFactory.class);
	
	private net.sf.otrcutmp4.model.xml.series.Series q;
	
	public XmlSeriesFactory(Query query){this(query.getSeries());}
	public XmlSeriesFactory(net.sf.otrcutmp4.model.xml.series.Series q){this.q=q;}
	
	public net.sf.otrcutmp4.model.xml.series.Series create(String format)
	{
		net.sf.otrcutmp4.model.xml.series.Series xml = new net.sf.otrcutmp4.model.xml.series.Series();
		
		return xml;
	}
	
	public net.sf.otrcutmp4.model.xml.series.Series build(Series<SERIES,SEASON,EPISODE,COVER> ejb)
	{
		logger.trace(ejb.toString());
		net.sf.otrcutmp4.model.xml.series.Series xml = new net.sf.otrcutmp4.model.xml.series.Series();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		if(q.isSetKey()){xml.setKey(ejb.getCode());}
		
		if(q.isSetSeason())
		{
			XmlSeasonFactory<SERIES,SEASON,EPISODE,COVER> f = new XmlSeasonFactory<SERIES,SEASON,EPISODE,COVER>(q.getSeason().get(0));
			for(Season<SERIES,SEASON,EPISODE,COVER> season : ejb.getSeasons())
			{
				xml.getSeason().add(f.build(season));
			}
		}
		
		return xml;
	}	
}