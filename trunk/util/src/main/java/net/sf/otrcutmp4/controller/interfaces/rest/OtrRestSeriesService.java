package net.sf.otrcutmp4.controller.interfaces.rest;

import net.sf.otrcutmp4.model.xml.otr.Otr;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.model.xml.series.Tag;
import net.sf.otrcutmp4.model.xml.series.Tags;

public interface OtrRestSeriesService
{
	public Tags getTags(String fileName);
	
	public Tag tag(long episodeId, String otrName);
	
	public Series series(long seriesId);
	public Otr allSeries();
}
