package net.sf.otrcutmp4.controller.web.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.otrcutmp4.interfaces.rest.OtrPodcastRest;
import net.sf.otrcutmp4.model.xml.rss.Rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class OtrPodcastRestService implements OtrPodcastRest
{
	final static Logger logger = LoggerFactory.getLogger(OtrPodcastRestService.class);
	
	@Override
	@GET @Path("/rss")
	@Produces(MediaType.APPLICATION_XML)
	public Rss rss()
	{
		Rss rss = new Rss();
		return rss;
	}
}