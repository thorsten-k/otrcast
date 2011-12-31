package net.sf.otrcutmp4.model.xml.xpath.otr;

import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.otrcutmp4.model.xml.otr.Download;
import net.sf.otrcutmp4.model.xml.otr.OtrId;
import net.sf.otrcutmp4.model.xml.xpath.OtrXpath;
import net.sf.otrcutmp4.test.AbstractOtrXmlTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXPathOtrOtrId extends AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXPathOtrOtrId.class);
    
	private Download download;
	private final String typeOk = "myType";
	private final String typeDuplicated = "myDupe";
	
	@Before
	public void initLinkList()
	{
		download = new Download();
		
		OtrId d1 = new OtrId();
		d1.setKey(typeOk);
		download.getOtrId().add(d1);
		
		OtrId d2 = new OtrId();
		d2.setKey("dummy");
		download.getOtrId().add(d2);
		
		OtrId d3 = new OtrId();
		d3.setKey(typeDuplicated);
		download.getOtrId().add(d3);
		
		OtrId d4 = new OtrId();
		d4.setKey(typeDuplicated);
		download.getOtrId().add(d4);
	}
	
	@Test
	public void find() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		OtrId test = OtrXpath.getOtrIdByKey(download, typeOk);
	    Assert.assertEquals(typeOk,test.getKey());
	}

	@Test(expected=ExlpXpathNotFoundException.class)
	public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		OtrXpath.getOtrIdByKey(download, "-1");
	}
	
	@Test(expected=ExlpXpathNotUniqueException.class)
	public void testDuplicate() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		OtrXpath.getOtrIdByKey(download, typeDuplicated);
	}
}