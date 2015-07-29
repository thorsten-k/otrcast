package de.kisner.otrcast.model.xml.otr;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractOtrXmlTest;

public abstract class AbstractXmlOtrTest extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlOtrTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/otr";
	protected static File fXml;
}