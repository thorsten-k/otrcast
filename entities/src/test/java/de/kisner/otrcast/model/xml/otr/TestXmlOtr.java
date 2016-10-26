package de.kisner.otrcast.model.xml.otr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.series.TestXmlEpisode;
import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlOtr extends AbstractXmlOtrTest<Otr>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOtr.class);
	
	public TestXmlOtr(){super(Otr.class);}
	public static Otr create(boolean withChildren){return (new TestXmlOtr()).build(withChildren);}
    
    public Otr build(boolean withChilds)
    {
    	Otr xml = new Otr();
    	
    	if(withChilds)
    	{
    		xml.getEpisode().add(TestXmlEpisode.create(false));xml.getEpisode().add(TestXmlEpisode.create(false));
    	}
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlOtr test = new TestXmlOtr();
		test.saveReferenceXml();
    }
}