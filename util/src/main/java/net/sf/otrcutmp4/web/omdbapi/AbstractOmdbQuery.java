package net.sf.otrcutmp4.web.omdbapi;

import net.sf.otrcutmp4.web.util.AbstractHttpXmlQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOmdbQuery extends AbstractHttpXmlQuery
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOmdbQuery.class);

	public AbstractOmdbQuery()
	{
		 // http://www.omdbapi.com/?t=True Grit&y=1969
		super("http","www.omdbapi.com","/");
	}
}