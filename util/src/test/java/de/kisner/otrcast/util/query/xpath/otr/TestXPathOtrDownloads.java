package de.kisner.otrcast.util.query.xpath.otr;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.otr.Download;
import de.kisner.otrcast.model.xml.otr.Linklist;
import de.kisner.otrcast.test.AbstractOtrcastTest;
import de.kisner.otrcast.util.query.xpath.OtrXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class TestXPathOtrDownloads extends AbstractOtrcastTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXPathOtrDownloads.class);
    
	private Linklist linklist;
	private final String typeOk = "myType";
	private final String typeDuplicated = "myDupe";
	
	@Before
	public void initLinkList()
	{
		linklist = new Linklist();
		
		Download d1 = new Download();
		d1.setType(typeOk);
		linklist.getDownload().add(d1);
		
		Download d2 = new Download();
		d2.setType("dummy");
		linklist.getDownload().add(d2);
		
		Download d3 = new Download();
		d3.setType(typeDuplicated);
		linklist.getDownload().add(d3);
		
		Download d4 = new Download();
		d4.setType(typeDuplicated);
		linklist.getDownload().add(d4);
	}
	
	@Test
	public void find() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Download test = OtrXpath.getDownload(linklist, typeOk);
	    Assert.assertEquals(typeOk,test.getType());
	}

	@Test(expected=ExlpXpathNotFoundException.class)
	public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		OtrXpath.getDownload(linklist, "-1");
	}
	
	@Test(expected=ExlpXpathNotUniqueException.class)
	public void testDuplicate() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		OtrXpath.getDownload(linklist, typeDuplicated);
	}
}