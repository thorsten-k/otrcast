package de.kisner.otrcast.interfaces.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/rest/test")
public interface OtrTestRest
{
	@GET @Path("time")
	@Produces(MediaType.TEXT_PLAIN)
	String getTime();
}
