package net.sf.otrcutmp4.controller.web.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.otrcutmp4.interfaces.rest.OtrMediacenterRest;
import net.sf.otrcutmp4.model.xml.container.Otr;

@Path("/rest/mediacenter")
public class OtrMediacenterRestService implements OtrMediacenterRest
{
	@Override
	@GET @Path("/series/all")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	public Otr allSeries()
	{
		
		return new Otr();
	}	
}