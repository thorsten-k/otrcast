package net.sf.otrcutmp4.model.xml.series;

import java.io.File;

import net.sf.otrcutmp4.test.AbstractOtrXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlSeriesTest extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSeriesTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/series";
	protected static File fXml;
}