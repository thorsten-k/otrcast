package net.sf.otrcutmp4.model.xml.otr;

import java.io.File;

import net.sf.otrcutmp4.test.AbstractXmlTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractXmlOtrTest extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(AbstractXmlOtrTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/otr";
	protected static File fXml;
}