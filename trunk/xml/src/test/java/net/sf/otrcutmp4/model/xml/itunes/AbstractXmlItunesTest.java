package net.sf.otrcutmp4.model.xml.itunes;

import java.io.File;

import net.sf.otrcutmp4.test.AbstractOtrXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlItunesTest extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlItunesTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/itunes";
	protected static File fXml;
}