package net.sf.otrcutmp4.web.tvdb;

import net.sf.otrcutmp4.web.util.AbstractHttpXmlQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractTvDbQuery extends AbstractHttpXmlQuery
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTvDbQuery.class);

    protected String apiKey;

	public AbstractTvDbQuery(String apiKey)
	{
		super("http://thetvdb.com/api");
        this.apiKey=apiKey;
	}
}