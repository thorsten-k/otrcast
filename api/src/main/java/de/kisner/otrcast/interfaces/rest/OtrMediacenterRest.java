package de.kisner.otrcast.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.mc.ServerStatus;
import de.kisner.otrcast.model.xml.video.tv.Movie;
import de.kisner.otrcast.model.xml.video.tv.Season;
import de.kisner.otrcast.model.xml.video.tv.Series;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

@Path("/rest/mediacenter")
public interface OtrMediacenterRest
{
	@GET @Path("/status")
	@Produces(MediaType.APPLICATION_XML)
	ServerStatus status();
	
	// ---------------------- Movies ----------------------
	@GET @Path("/movies")
	@Produces(MediaType.APPLICATION_XML)
	Otr allMovies();
	
	@GET @Path("/movies/{id}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	Movie movie(@PathParam("id") long movieId) throws UtilsNotFoundException;
	
	// ---------------------- Series ----------------------
	@GET @Path("/series")
	@Produces(MediaType.APPLICATION_XML)
	Otr allSeries();
	
	@GET @Path("/series/{id}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	Series seriesAll(@PathParam("id") long seriesId) throws UtilsNotFoundException;
	
	@GET @Path("/series/{id}/season")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	Series seriesWithSeason(@PathParam("id") long seriesId) throws UtilsNotFoundException;
	
	@GET @Path("/series/{id}/season/episode")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	Season seasonWithEpisode(@PathParam("id") long seasonId) throws UtilsNotFoundException;
}