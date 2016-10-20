package de.kisner.otrcast.model.xml.cut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlComment extends AbstractXmlCutTest<Comment>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlComment.class);
	
	public TestXmlComment(){super(Comment.class);}
	public static Comment create(boolean withChildren){return (new TestXmlComment()).build(withChildren);}
    
    public Comment build(boolean withChilds)
    {
    	Comment xml = new Comment();
    	xml.setValue("myComment");
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlComment test = new TestXmlComment();
		test.saveReferenceXml();
    }
}