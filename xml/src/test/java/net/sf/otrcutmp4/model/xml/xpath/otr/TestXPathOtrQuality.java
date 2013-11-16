package net.sf.otrcutmp4.model.xml.xpath.otr;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.otrcutmp4.model.xml.otr.OtrId;
import net.sf.otrcutmp4.model.xml.otr.Quality;
import net.sf.otrcutmp4.xml.xpath.OtrXpath;
import net.sf.otrcutmp4.test.AbstractOtrXmlTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXPathOtrQuality extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXPathOtrQuality.class);
    
	private OtrId otrId;
	private final String typeOk = "myType";
	private final String typeDuplicated = "myDupe";
	
	@Before
	public void initLinkList()
	{
		otrId = new OtrId();
		
		Quality d1 = new Quality();
		d1.setType(typeOk);
		otrId.getQuality().add(d1);
		
		Quality d2 = new Quality();
		d2.setType("dummy");
		otrId.getQuality().add(d2);
		
		Quality d3 = new Quality();
		d3.setType(typeDuplicated);
		otrId.getQuality().add(d3);
		
		Quality d4 = new Quality();
		d4.setType(typeDuplicated);
		otrId.getQuality().add(d4);
	}
	
	@Test
	public void find() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Quality test = OtrXpath.getQualityByKey(otrId, typeOk);
	    Assert.assertEquals(typeOk,test.getType());
	}

	@Test(expected=ExlpXpathNotFoundException.class)
	public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		OtrXpath.getQualityByKey(otrId, "-1");
	}
	
	@Test(expected=ExlpXpathNotUniqueException.class)
	public void testDuplicate() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		OtrXpath.getQualityByKey(otrId, typeDuplicated);
	}
}