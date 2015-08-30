package de.kisner.otrcast.interfaces.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/rest/simple")
public interface OtrSimpleRest
{	
	@GET @Produces(MediaType.TEXT_PLAIN)
	public String hello();

	@GET @Path("/books") @Produces(MediaType.TEXT_PLAIN)
	public String getBooks();

	@GET @Path("/book/{isbn}") @Produces(MediaType.TEXT_PLAIN)
	public String getBook(@PathParam("isbn") String id);
}
