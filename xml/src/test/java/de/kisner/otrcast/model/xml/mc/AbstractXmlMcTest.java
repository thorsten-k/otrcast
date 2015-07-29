package de.kisner.otrcast.model.xml.mc;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractOtrXmlTest;

public abstract class AbstractXmlMcTest extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlMcTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/mc";
	protected static File fXml;
}