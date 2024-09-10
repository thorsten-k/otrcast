package de.kisner.otrcast.model.xml.video;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractOtrXmlTest;

public abstract class AbstractXmlVideoTest <T extends Object> extends AbstractOtrXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlVideoTest.class);

	public AbstractXmlVideoTest(Class<T> cXml)
	{
		super(cXml,Paths.get("video"));
	}
}