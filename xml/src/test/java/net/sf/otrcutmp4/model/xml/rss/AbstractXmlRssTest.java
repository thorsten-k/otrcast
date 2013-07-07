package net.sf.otrcutmp4.model.xml.rss;

import java.io.File;

import net.sf.otrcutmp4.test.AbstractOtrXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlRssTest extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlRssTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/rss";
	protected static File fXml;
}