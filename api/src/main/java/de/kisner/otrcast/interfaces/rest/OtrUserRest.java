package de.kisner.otrcast.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.series.Tags;

@Path("/rest/user")
public interface OtrUserRest
{	
	@POST @Path("/scan")
	@Consumes(MediaType.APPLICATION_XML)
	String scan(VideoFiles vfs);
	
	@GET @Path("/processed/cutlist/{id}") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.TEXT_PLAIN)
	Tags processedCutlist(@PathParam("id") long cutlistId);
}
