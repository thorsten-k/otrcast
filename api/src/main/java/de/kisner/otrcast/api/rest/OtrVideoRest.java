package de.kisner.otrcast.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Series;
import de.kisner.otrcast.model.xml.series.Tags;

@Path("/rest/series")
public interface OtrVideoRest
{
	@GET @Path("/tags/{id}") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.TEXT_PLAIN)
	Tags getTags(@PathParam("id") String fileName);
		
	@GET @Path("/episode/{id}") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.TEXT_PLAIN)
	Episode getEpisode(@PathParam("id") long episodeId);
	
	@GET @Path("/episode/{seriesId}/{seasonNr}/{episodeNr}") @Produces(MediaType.APPLICATION_XML)
	Episode getEpisode(@PathParam("seriesId") long seriesId, @PathParam("seasonNr") long seasonNr, @PathParam("episodeNr") long episodeNr);
	
	@POST @Path("/episode/info") @Consumes(MediaType.APPLICATION_XML) @Produces(MediaType.APPLICATION_XML)
	Otr resolveEpisode(Episode episode);
	
	@POST @Path("/series/info") @Consumes(MediaType.APPLICATION_XML) @Produces(MediaType.APPLICATION_XML)
	Otr resolveSeries(Series series);
	
	
}