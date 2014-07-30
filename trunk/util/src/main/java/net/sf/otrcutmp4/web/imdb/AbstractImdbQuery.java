package net.sf.otrcutmp4.web.imdb;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.web.util.AbstractHttpXmlQuery;

import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractImdbQuery extends AbstractHttpXmlQuery
{
	final static Logger logger = LoggerFactory.getLogger(AbstractImdbQuery.class);

	public AbstractImdbQuery()
	{
		super("http","www.imdb.com","/xml/find");
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