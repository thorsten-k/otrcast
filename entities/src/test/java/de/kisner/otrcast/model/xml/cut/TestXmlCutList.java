package de.kisner.otrcast.model.xml.cut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.series.TestXmlVideo;
import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlCutList extends AbstractXmlCutTest<CutList>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCutList.class);
	
	public TestXmlCutList(){super(CutList.class);}
	public static CutList create(boolean withChildren){return (new TestXmlCutList()).build(withChildren);}
    
    public CutList build(boolean withChilds)
    {
    	CutList xml = new CutList();
    	xml.setId("myId");
    	xml.setRating(12.3);
    	xml.setRatingCount(3);
    	
    	if(withChilds)
    	{
    		xml.setAuthor(TestXmlAuthor.create(false));
    		xml.setComment(TestXmlComment.create(false));
    		xml.setFileName(TestXmlFileName.create(false));
    		xml.setName(TestXmlName.create(false));
    		xml.getCut().add(TestXmlCut.create(false));
    		xml.getCut().add(TestXmlCut.create(false));
    		xml.getVideo().add(TestXmlVideo.create(false));
    		xml.getVideo().add(TestXmlVideo.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlCutList test = new TestXmlCutList();
		test.saveReferenceXml();
    }
}