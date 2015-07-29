package de.kisner.otrcast.model.xml.cut;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.cut.Comment;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlComment extends AbstractXmlCutTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlComment.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"comment.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	Comment actual = create();
    	Comment expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Comment.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Comment create(){return create(true);}
    public static Comment create(boolean withChilds)
    {
    	Comment xml = new Comment();
    	xml.setValue("myComment");
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlComment.initFiles();	
		TestXmlComment test = new TestXmlComment();
		test.save();
    }
}