package de.kisner.otrcast.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.series.Videos;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;

@Path("/rest/cut")
public interface OtrCutRest
{
	@POST @Path("/addSelection")
	@Consumes(MediaType.APPLICATION_XML)
	String addCutPackage(VideoFiles vFiles) throws UtilsProcessingException;
	
	@GET @Path("/getSelection/{token}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	Videos findCutPackage(@PathParam("token") String token) throws UtilsProcessingException;
}