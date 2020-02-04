package de.kisner.otrcast.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.exception.processing.UtilsProcessingException;

import de.kisner.otrcast.bl.OtrCutBlRest;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.video.Videos;

@Path("/rest/cut")
public interface OtrCutRest extends OtrCutBlRest
{
	@POST @Path("/addSelection")
	@Consumes(MediaType.APPLICATION_XML)
	@Override String addCutPackage(VideoFiles vFiles) throws UtilsProcessingException;
	
	@GET @Path("/getSelection/{token}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	@Override Videos findCutPackage(@PathParam("token") String token) throws UtilsProcessingException;
}