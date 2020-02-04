package de.kisner.otrcast.web.myapifilms;

import org.jdom2.Document;
import org.jeesl.exception.processing.UtilsProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.web.util.AbstractHttpXmlQuery;

public class AbstractMafQuery extends AbstractHttpXmlQuery
{
	final static Logger logger = LoggerFactory.getLogger(AbstractMafQuery.class);

	public AbstractMafQuery()
	{
		super("http","www.myapifilms.com","/imdb");
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