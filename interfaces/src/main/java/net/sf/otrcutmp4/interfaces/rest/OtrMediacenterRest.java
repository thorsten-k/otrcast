package net.sf.otrcutmp4.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.otrcutmp4.model.xml.container.Otr;

@Path("/rest/mediacenter")
public interface OtrMediacenterRest
{
	@GET @Path("/series/all")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	Otr allSeries();
}