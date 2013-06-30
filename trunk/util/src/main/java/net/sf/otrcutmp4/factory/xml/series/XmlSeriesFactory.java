package net.sf.otrcutmp4.factory.xml.series;

import net.sf.otrcutmp4.model.xml.otr.Query;
import net.sf.otrcutmp4.model.xml.series.Series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSeriesFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlSeriesFactory.class);
	
	private Series q;
	
	public XmlSeriesFactory(Query query){this(query.getSeries());}
	public XmlSeriesFactory(Series q){this.q=q;}
	
	public Series create(String format)
	{
		Series xml = new Series();
		
		return xml;
	}
	
	public Series build(net.sf.otrcutmp4.interfaces.model.Series ejb)
	{
		Series xml = new Series();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		if(q.isSetKey()){xml.setKey(ejb.getCode());}
		
		if(q.isSetSeason())
		{
			XmlSeasonFactory f = new XmlSeasonFactory(q.getSeason().get(0));
//			for(net.sf.otrcutmp4.interfaces.model.Season season : ejb.getSeasons())
			{
				
			}
		}
		
		return xml;
	}
	
}
