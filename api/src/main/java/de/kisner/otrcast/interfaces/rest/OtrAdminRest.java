package de.kisner.otrcast.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.exception.processing.UtilsProcessingException;

import de.kisner.otrcast.model.xml.otr.Format;
import de.kisner.otrcast.model.xml.otr.Quality;
import de.kisner.otrcast.model.xml.video.tv.Category;
import de.kisner.otrcast.model.xml.video.tv.Episode;
import de.kisner.otrcast.model.xml.video.tv.Series;
import net.sf.ahtutils.xml.access.Access;

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
	
	@POST @Path("security")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	String applySecurity(Access views, Access roles);
}
