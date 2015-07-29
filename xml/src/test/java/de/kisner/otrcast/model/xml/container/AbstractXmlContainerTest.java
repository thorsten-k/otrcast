package de.kisner.otrcast.model.xml.container;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractOtrXmlTest;

public abstract class AbstractXmlContainerTest extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlContainerTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/container";
	protected static File fXml;
}