package net.sf.otrcutmp4.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.otrcutmp4.model.xml.user.User;

@Path("/rest/user")
public interface OtrUserRest
{
	@POST @Path("/register")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	User register(User user);
	
}
