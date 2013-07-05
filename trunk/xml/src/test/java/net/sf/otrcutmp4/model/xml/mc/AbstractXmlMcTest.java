package net.sf.otrcutmp4.model.xml.mc;

import java.io.File;

import net.sf.otrcutmp4.test.AbstractOtrXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlMcTest extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlMcTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/mc";
	protected static File fXml;
}