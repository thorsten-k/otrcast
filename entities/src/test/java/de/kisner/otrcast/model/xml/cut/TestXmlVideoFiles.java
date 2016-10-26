package de.kisner.otrcast.model.xml.cut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlVideoFiles extends AbstractXmlCutTest<VideoFiles>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlVideoFiles.class);
	
	public TestXmlVideoFiles(){super(VideoFiles.class);}
	public static VideoFiles create(boolean withChildren){return (new TestXmlVideoFiles()).build(withChildren);}
    
    public VideoFiles build(boolean withChilds)
    {
    	VideoFiles xml = new VideoFiles();
    	
    	if(withChilds)
    	{
    		xml.getVideoFile().add(TestXmlVideoFile.create(false));
    		xml.getVideoFile().add(TestXmlVideoFile.create(false));
    	}
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlVideoFiles test = new TestXmlVideoFiles();
		test.saveReferenceXml();
    }
}