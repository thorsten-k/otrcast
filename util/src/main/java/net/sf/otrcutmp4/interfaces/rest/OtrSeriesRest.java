package net.sf.otrcutmp4.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Tags;

@Path("/rest/series")
public interface OtrSeriesRest
{
	@GET @Path("/tags/{id}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	Tags getTags(@PathParam("id") String fileName);
	
	@GET @Path("/episode/{id}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	Episode getEpisode(@PathParam("id") long episodeId);
	
//	public Tag tag(long episodeId, String otrName);
	
//	public Otr allSeries();
//	public Series series(long seriesId);
	
}
