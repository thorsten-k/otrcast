package de.kisner.otrcast.model.xml.tvdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractOtrXmlTest;

public abstract class AbstractXmlTvDbTest <T extends Object> extends AbstractOtrXmlTest<T> 
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlTvDbTest.class);

	public AbstractXmlTvDbTest(Class<T> cXml)
	{
		super(cXml,"tvdb");
	}
}