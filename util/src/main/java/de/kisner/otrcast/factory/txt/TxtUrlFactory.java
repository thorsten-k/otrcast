package de.kisner.otrcast.factory.txt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.web.UrlGenerator;

public class TxtUrlFactory implements UrlGenerator
{
	final static Logger logger = LoggerFactory.getLogger(TxtUrlFactory.class);

	private String url;
	
	public TxtUrlFactory()
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