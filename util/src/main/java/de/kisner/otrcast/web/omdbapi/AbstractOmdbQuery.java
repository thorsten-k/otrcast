package de.kisner.otrcast.web.omdbapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.web.util.AbstractHttpXmlQuery;

public class AbstractOmdbQuery extends AbstractHttpXmlQuery
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOmdbQuery.class);

	public AbstractOmdbQuery()
	{
		 // http://www.omdbapi.com/?t=True Grit&y=1969
		super("http","www.omdbapi.com","/");
	}
}