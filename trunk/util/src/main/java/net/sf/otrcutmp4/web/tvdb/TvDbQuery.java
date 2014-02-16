package net.sf.otrcutmp4.web.tvdb;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TvDbQuery extends AbstractTvDbQuery
{
	final static Logger logger = LoggerFactory.getLogger(TvDbQuery.class);

	public TvDbQuery(String apiKey)
	{
        super(apiKey);
	}
	
	public Date getLastModificationTime()
	{
        TvDbSyncQuery q = new TvDbSyncQuery(apiKey);
        return q.debug();
	}
}