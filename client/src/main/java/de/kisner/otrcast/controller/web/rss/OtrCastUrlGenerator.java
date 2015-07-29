package de.kisner.otrcast.controller.web.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.web.UrlGenerator;

public class OtrCastUrlGenerator implements UrlGenerator
{
	final static Logger logger = LoggerFactory.getLogger(OtrCastUrlGenerator.class);

	private String url;
	
	public OtrCastUrlGenerator()
	{
		this.url="http://localhost:8080/servlet";
	}
	
	@Override public String enclosure(long id)
	{
		return url+"/file/"+id+".mp4";
	}

	@Override
	public String image(long id,String type)
	{
		return url+"/image/"+id+"."+type;
	}
	
	
}