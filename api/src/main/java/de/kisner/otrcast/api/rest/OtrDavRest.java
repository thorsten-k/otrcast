package de.kisner.otrcast.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.kisner.otrcast.model.xml.container.Otr;

@Path("/rest/dav")
public interface OtrDavRest
{
	@GET @Path("/content/{user}") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.TEXT_PLAIN)
	Otr getContent(@PathParam("user") String user);	
}