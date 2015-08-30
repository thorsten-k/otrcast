package de.kisner.otrcast.model.xml.cut;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractOtrXmlTest;

public abstract class AbstractXmlCutTest extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlCutTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/cut";
	protected static File fXml;
}