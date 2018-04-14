package de.kisner.otrcast.dav;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.app.OtrWebDavServer;
import de.kisner.otrcast.model.xml.video.tv.Episode;
import io.milton.annotations.ContentLength;
import io.milton.annotations.Name;

public class DavUser
{
	final static Logger logger = LoggerFactory.getLogger(OtrWebDavServer.class);
	
	private final String name;
	
	public DavUser(String name)
    {
		this.name=name;
    }

	public String getName()
	{
		logger.info("Requesting name");
		return "x";
	}
	
	public String getPassword()
	{
		logger.info("Requesting password");
		return "y";
	}
}