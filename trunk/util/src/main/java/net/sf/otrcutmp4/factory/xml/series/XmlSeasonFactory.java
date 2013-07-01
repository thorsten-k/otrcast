package net.sf.otrcutmp4.factory.xml.series;

import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.model.xml.otr.Query;

public class XmlSeasonFactory<SERIES extends Series<SERIES,SEASON,EPISODE>, SEASON extends Season<SERIES,SEASON,EPISODE>,EPISODE extends Episode<SERIES,SEASON,EPISODE>>
{	
	private net.sf.otrcutmp4.model.xml.series.Season q;
	
	public XmlSeasonFactory(Query query){this(query.getSeason());}
	public XmlSeasonFactory(net.sf.otrcutmp4.model.xml.series.Season q){this.q=q;}
	
	public net.sf.otrcutmp4.model.xml.series.Season build(Season<SERIES,SEASON,EPISODE> ejb)
	{
		net.sf.otrcutmp4.model.xml.series.Season xml = new net.sf.otrcutmp4.model.xml.series.Season();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetNr()){xml.setNr(ejb.getNr());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		
		if(q.isSetSeries())
		{
			XmlSeriesFactory<SERIES,SEASON,EPISODE> f = new XmlSeriesFactory<SERIES,SEASON,EPISODE>(q.getSeries());
			xml.setSeries(f.build(ejb.getSeries()));
		}
		
		return xml;
	}
}