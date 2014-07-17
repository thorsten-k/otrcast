package net.sf.otrcutmp4.web.maf;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.web.util.AbstractHttpXmlQuery;

import org.jdom2.Document;
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
	
	protected void checkForError(Document doc) throws UtilsProcessingException
	{
		logger.trace("Error Checking");
		if(doc.getRootElement().getName().equals("error"))
		{
			String message = doc.getRootElement().getChildText("message");
			logger.trace(message);
			throw new UtilsProcessingException(message);
		}
	}
}