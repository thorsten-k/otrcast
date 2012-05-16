package net.sf.otrcutmp4.interfaces.rest;

import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.model.xml.series.Category;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.model.xml.series.Tag;
import net.sf.otrcutmp4.model.xml.series.Tags;

public interface OtrSeriesRest
{
	public Tags getTags(String otrId);
	public Tag tag(long episodeId, String otrName);
	
	public Otr allSeries();
	public Series series(long seriesId);
	
	
	@Deprecated public Category addCategory(Category category);
	@Deprecated public Series addSeries(Series series);
	@Deprecated public Episode addEpisode(Episode episode);
}
