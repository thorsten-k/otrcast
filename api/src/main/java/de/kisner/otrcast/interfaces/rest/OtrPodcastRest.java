package de.kisner.otrcast.interfaces.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.kisner.otrcast.model.xml.rss.Rss;

@Path("/rest/podcast")
public interface OtrPodcastRest
{
	@GET @Path("/rss")
	@Produces(MediaType.APPLICATION_XML)
	Rss rss();
}