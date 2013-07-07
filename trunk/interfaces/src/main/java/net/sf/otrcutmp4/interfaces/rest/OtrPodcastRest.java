package net.sf.otrcutmp4.interfaces.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.otrcutmp4.model.xml.rss.Rss;

@Path("/")
public interface OtrPodcastRest
{
	@GET @Path("/rss")
	@Produces(MediaType.APPLICATION_XML)
	Rss rss();
}