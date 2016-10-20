package de.kisner.otrcast.model.xml.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractOtrXmlTest;

public abstract class AbstractXmlSeriesTest <T extends Object> extends AbstractOtrXmlTest<T> 
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSeriesTest.class);

	public AbstractXmlSeriesTest(Class<T> cXml)
	{
		super(cXml,"series");
	}
}