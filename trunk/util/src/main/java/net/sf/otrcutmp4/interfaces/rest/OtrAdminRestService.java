package net.sf.otrcutmp4.interfaces.rest;

import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.Quality;
import net.sf.otrcutmp4.model.xml.series.Series;

public interface OtrAdminRestService
{
	Series addSeries(Series series);
	Format addFormat(Format format);
	Quality addQuality(Quality quality);
}
