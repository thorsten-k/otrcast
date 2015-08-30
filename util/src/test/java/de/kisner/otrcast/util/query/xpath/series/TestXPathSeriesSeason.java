package de.kisner.otrcast.util.query.xpath.series;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.series.Season;
import de.kisner.otrcast.model.xml.series.Series;
import de.kisner.otrcast.test.AbstractOtrXmlTest;
import de.kisner.otrcast.util.query.xpath.SeriesXpath;

public class TestXPathSeriesSeason extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXPathSeriesSeason.class);
    
	private Series container;
	private final long typeOk = 1;
	private final long typeDuplicated = 2;
	
	@Before
	public void initLinkList()
	{
		container = new Series();
		
		Season child1 = new Season();
		child1.setNr(typeOk);
		container.getSeason().add(child1);
		
		Season child2 = new Season();
		child2.setNr(0);
		container.getSeason().add(child2);
		
		Season child3 = new Season();
		child3.setNr(typeDuplicated);
		container.getSeason().add(child3);
		
		Season child4 = new Season();
		child4.setNr(typeDuplicated);
		container.getSeason().add(child4);
	}
	
	@Test
	public void find() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Season actual = SeriesXpath.getSeason(container, typeOk);
	    Assert.assertEquals(typeOk,actual.getNr());
	}

	@Test(expected=ExlpXpathNotFoundException.class)
	public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		SeriesXpath.getSeason(container, -1);
	}
	
	@Test(expected=ExlpXpathNotUniqueException.class)
	public void testDuplicate() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		SeriesXpath.getSeason(container, typeDuplicated);
	}
}