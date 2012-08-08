package net.sf.otrcutmp4.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

@Path("/rest/user")
public interface OtrUserRest
{	
	@POST @Path("/scan")
	@Consumes(MediaType.APPLICATION_XML)
	String scan(VideoFiles vf);
}
