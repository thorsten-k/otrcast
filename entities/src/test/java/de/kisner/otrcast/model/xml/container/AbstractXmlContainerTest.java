package de.kisner.otrcast.model.xml.container;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlContainerTest <T extends Object> extends AbstractXmlTest<T> 
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlContainerTest.class);
	
	public AbstractXmlContainerTest(Class<T> cXml)
	{
		super(cXml,"container");
	}
}