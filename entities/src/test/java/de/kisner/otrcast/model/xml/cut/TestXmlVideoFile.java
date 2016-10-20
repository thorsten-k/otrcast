package de.kisner.otrcast.model.xml.cut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.otr.TestXmlOtrId;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlVideoFile extends AbstractXmlCutTest<VideoFile>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlVideoFile.class);
	
	public TestXmlVideoFile(){super(VideoFile.class);}
	public static VideoFile create(boolean withChildren){return (new TestXmlVideoFile()).build(withChildren);}
    
    public VideoFile build(boolean withChilds)
    {
    	VideoFile xml = new VideoFile();
    	
    	if(withChilds)
    	{
    		xml.setFileName(TestXmlFileName.create(false));
    		xml.setOtrId(TestXmlOtrId.create(false));
    		xml.setCutList(TestXmlCutList.create(false));
    		xml.setCutLists(TestXmlCutLists.create(false));
    	}
    	
    	return xml;
    }

	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlVideoFile test = new TestXmlVideoFile();
		test.saveReferenceXml();
    }
}