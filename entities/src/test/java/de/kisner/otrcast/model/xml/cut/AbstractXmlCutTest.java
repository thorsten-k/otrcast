package de.kisner.otrcast.model.xml.cut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractOtrXmlTest;

public abstract class AbstractXmlCutTest <T extends Object> extends AbstractOtrXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlCutTest.class);
	
	public AbstractXmlCutTest(Class<T> cXml)
	{
		super(cXml,"cut");
	}
}