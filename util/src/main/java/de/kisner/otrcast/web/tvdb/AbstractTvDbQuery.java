package de.kisner.otrcast.web.tvdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.web.util.AbstractHttpXmlQuery;

public class AbstractTvDbQuery extends AbstractHttpXmlQuery
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTvDbQuery.class);

    protected String apiKey;

	public AbstractTvDbQuery(String apiKey)
	{
		super("http","thetvdb.com","api");
        this.apiKey=apiKey;
	}
}