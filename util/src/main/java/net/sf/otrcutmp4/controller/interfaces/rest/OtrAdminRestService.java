package net.sf.otrcutmp4.controller.interfaces.rest;

import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.Quality;

public interface OtrAdminRestService
{
	Format addFormat(Format format);
	Quality addQuality(Quality quality);
}
