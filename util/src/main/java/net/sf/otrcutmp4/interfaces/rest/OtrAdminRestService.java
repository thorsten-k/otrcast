package net.sf.otrcutmp4.interfaces.rest;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.Quality;
import net.sf.otrcutmp4.model.xml.series.Series;

public interface OtrAdminRestService
{
	Series addSeries(Series series) throws UtilsProcessingException;
	Format addFormat(Format format) throws UtilsProcessingException;
	Quality addQuality(Quality quality) throws UtilsProcessingException;
}
