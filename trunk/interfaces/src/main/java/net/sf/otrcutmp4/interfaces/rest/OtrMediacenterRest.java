package net.sf.otrcutmp4.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.model.xml.mc.ServerStatus;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;

@Path("/rest/mediacenter")
public interface OtrMediacenterRest
{
	@GET @Path("/status")
	@Produces(MediaType.APPLICATION_XML)
	ServerStatus status();
	
	@GET @Path("/series/all")
	@Produces(MediaType.APPLICATION_XML)
	Otr allSeries();
	
	@GET @Path("/series/{id}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	Series seriesWithSeason(@PathParam("id") long seriesId) throws UtilsNotFoundException;
	
	@GET @Path("/series/season/{id}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	Season seasonWithEpisode(@PathParam("id") long seasonId) throws UtilsNotFoundException;
}