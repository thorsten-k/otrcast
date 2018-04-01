package de.kisner.otrcast.dav;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.app.OtrWebDavServer;
import io.milton.annotations.Name;

public class DavEpisode
{
	final static Logger logger = LoggerFactory.getLogger(OtrWebDavServer.class);
	
	public DavEpisode(String code)
    {
        this.code=code;
    }
	


	private String code;

	@Name
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
