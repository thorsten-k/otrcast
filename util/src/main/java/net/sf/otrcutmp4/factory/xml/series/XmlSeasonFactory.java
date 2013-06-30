package net.sf.otrcutmp4.factory.xml.series;

import net.sf.otrcutmp4.model.xml.otr.Query;
import net.sf.otrcutmp4.model.xml.series.Season;

public class XmlSeasonFactory
{	
	private Season q;
	
	public XmlSeasonFactory(Query query){this(query.getSeason());}
	public XmlSeasonFactory(Season q){this.q=q;}
	
	public Season build(net.sf.otrcutmp4.interfaces.model.Season ejb)
	{
		Season xml = new Season();
		if(q.isSetNr()){xml.setNr(ejb.getNr());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		
		if(q.isSetSeries())
		{
			XmlSeriesFactory f = new XmlSeriesFactory(q.getSeries());
			xml.setSeries(f.build(ejb.getSeries()));
		}
		
		return xml;
	}
}
