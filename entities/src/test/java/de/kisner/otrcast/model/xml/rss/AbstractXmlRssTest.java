package de.kisner.otrcast.model.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractOtrXmlTest;

public abstract class AbstractXmlRssTest <T extends Object> extends AbstractOtrXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlRssTest.class);

	public AbstractXmlRssTest(Class<T> cXml)
	{
		super(cXml,"rss");
	}
}