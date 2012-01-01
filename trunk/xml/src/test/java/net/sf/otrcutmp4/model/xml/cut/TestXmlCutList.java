package net.sf.otrcutmp4.model.xml.cut;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCutList extends AbstractXmlCutTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCutList.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"cutList.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	CutList actual = create();
    	CutList expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), CutList.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static CutList create(){return create(true);}
    public static CutList create(boolean withChilds)
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
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlCutList.initFiles();	
		TestXmlCutList test = new TestXmlCutList();
		test.save();
    }
}