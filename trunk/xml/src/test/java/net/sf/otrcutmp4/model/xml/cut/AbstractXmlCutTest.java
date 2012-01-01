package net.sf.otrcutmp4.model.xml.cut;

import java.io.File;

import net.sf.otrcutmp4.test.AbstractOtrXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlCutTest extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlCutTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/cut";
	protected static File fXml;
}