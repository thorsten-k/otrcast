package net.sf.otrcutmp4.web.maf;

import net.sf.otrcutmp4.web.util.AbstractHttpXmlQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractMafQuery extends AbstractHttpXmlQuery
{
	final static Logger logger = LoggerFactory.getLogger(AbstractMafQuery.class);

	public AbstractMafQuery()
	{
		super("http://www.myapifilms.com/imdb");
		httpProtocol = "http";
		httpHost = "www.myapifilms.com";
		httpContext = "/imdb";
	}
}