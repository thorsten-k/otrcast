package net.sf.otrcutmp4.controller.web.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.otrcutmp4.interfaces.web.UrlGenerator;

public class DefaultUrlGenerator implements UrlGenerator
{
	final static Logger logger = LoggerFactory.getLogger(DefaultUrlGenerator.class);

	@Override public String enclosure(long id)
	{
		return "default-"+id;
	}

	@Override
	public String image(long id, String type)
	{
		return "default-"+id+"."+type;
	}
}