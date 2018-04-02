package de.kisner.otrcast.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.series.Videos;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;

@Path("/rest/dav")
public interface OtrDavRest
{
	@GET @Path("/content/{user}") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.TEXT_PLAIN)
	Otr getContent(@PathParam("user") String user);	
	
	@POST @Path("/repository/upload")
	@Consumes(MediaType.APPLICATION_XML)
	String uploadRepository(Videos videos) throws UtilsProcessingException;
}