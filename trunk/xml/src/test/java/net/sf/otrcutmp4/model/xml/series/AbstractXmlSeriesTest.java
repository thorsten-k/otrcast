package net.sf.otrcutmp4.model.xml.series;

import java.io.File;

import net.sf.otrcutmp4.test.AbstractXmlTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractXmlSeriesTest extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(AbstractXmlSeriesTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/series";
	protected static File fXml;
}