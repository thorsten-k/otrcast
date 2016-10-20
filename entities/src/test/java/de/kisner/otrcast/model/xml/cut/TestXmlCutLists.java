package de.kisner.otrcast.model.xml.cut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlCutLists extends AbstractXmlCutTest<CutLists>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCutLists.class);
	
	public TestXmlCutLists(){super(CutLists.class);}
	public static CutLists create(boolean withChildren){return (new TestXmlCutLists()).build(withChildren);}
    
    public CutLists build(boolean withChilds)
    {
    	CutLists xml = new CutLists();
    	
    	if(withChilds)
    	{
    		xml.getCutList().add(TestXmlCutList.create(false));
    		xml.getCutList().add(TestXmlCutList.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlCutLists test = new TestXmlCutLists();
		test.saveReferenceXml();
    }
}