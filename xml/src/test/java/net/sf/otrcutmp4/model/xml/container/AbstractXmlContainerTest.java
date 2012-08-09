package net.sf.otrcutmp4.model.xml.container;

import java.io.File;

import net.sf.otrcutmp4.test.AbstractOtrXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlContainerTest extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlContainerTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/container";
	protected static File fXml;
}