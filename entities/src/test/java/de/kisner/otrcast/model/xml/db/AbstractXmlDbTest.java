package de.kisner.otrcast.model.xml.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractOtrXmlTest;

public abstract class AbstractXmlDbTest <T extends Object> extends AbstractOtrXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlDbTest.class);
	
	public AbstractXmlDbTest(Class<T> cXml)
	{
		super(cXml,"db");
	}
}