package net.sf.otrcutmp4.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.Quality;
import net.sf.otrcutmp4.model.xml.series.Category;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Series;

@Path("/rest/admin")
public interface OtrAdminRest
{
	@POST @Path("/add/format")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	Format addFormat(Format format) throws UtilsProcessingException;
	
	@POST @Path("/add/series")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	Series addSeries(Series series) throws UtilsProcessingException;
	
	@POST @Path("/add/quality")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	Quality addQuality(Quality quality) throws UtilsProcessingException;
	
	@POST @Path("/add/category")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	Category addCategory(Category category);
	
	@POST @Path("/add/episode")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	Episode addEpisode(Episode episode);
}
