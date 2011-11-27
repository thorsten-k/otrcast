package net.sf.otrcutmp4.model.xml.otr;

import java.io.File;

import net.sf.otrcutmp4.test.AbstractXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlOtrTest extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlOtrTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/otr";
	protected static File fXml;
}