package net.sf.otrcutmp4.model.xml.otr;

import java.io.File;

import net.sf.otrcutmp4.test.AbstractOtrXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlOtrTest extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlOtrTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/otr";
	protected static File fXml;
}