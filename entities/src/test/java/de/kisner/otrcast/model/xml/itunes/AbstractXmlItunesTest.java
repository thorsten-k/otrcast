package de.kisner.otrcast.model.xml.itunes;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractOtrXmlTest;

public abstract class AbstractXmlItunesTest extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlItunesTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/itunes";
	protected static File fXml;
}