package de.kisner.otrcast.model.xml.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractOtrXmlTest;

public abstract class AbstractXmlContainerTest <T extends Object> extends AbstractOtrXmlTest<T> 
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlContainerTest.class);
	
	public AbstractXmlContainerTest(Class<T> cXml)
	{
		super(cXml,"container");
	}
}