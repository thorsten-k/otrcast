package de.kisner.otrcast.model.xml.rss;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractOtrXmlTest;

public abstract class AbstractXmlRssTest extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlRssTest.class);

    protected static final String dirSuffix = "rss";

	protected static final String rootDir = "src/test/resources/data/xml/rss";
	protected static File fXml;
}