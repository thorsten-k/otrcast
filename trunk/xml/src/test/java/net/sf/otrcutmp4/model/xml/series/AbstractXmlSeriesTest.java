package net.sf.otrcutmp4.model.xml.series;

import java.io.File;

import net.sf.otrcutmp4.test.AbstractXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlSeriesTest extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSeriesTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/series";
	protected static File fXml;
}