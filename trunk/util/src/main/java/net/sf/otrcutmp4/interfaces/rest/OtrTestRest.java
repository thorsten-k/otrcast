package net.sf.otrcutmp4.interfaces.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/rest/test")
public interface OtrTestRest
{
	@GET @Path("/time")
	String getTime();
}
