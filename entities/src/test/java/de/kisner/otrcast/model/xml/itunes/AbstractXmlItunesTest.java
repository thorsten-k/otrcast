package de.kisner.otrcast.model.xml.itunes;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractOtrXmlTest;

public abstract class AbstractXmlItunesTest <T extends Object> extends AbstractOtrXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlItunesTest.class);
	
	public AbstractXmlItunesTest(Class<T> cXml)
	{
		super(cXml,Paths.get("itunes"));
	}
}