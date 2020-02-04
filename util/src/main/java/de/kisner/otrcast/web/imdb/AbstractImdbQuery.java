package de.kisner.otrcast.web.imdb;

import org.jdom2.Document;
import org.jeesl.exception.processing.UtilsProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.web.util.AbstractHttpXmlQuery;

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