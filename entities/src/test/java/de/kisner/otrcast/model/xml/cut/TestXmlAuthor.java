package de.kisner.otrcast.model.xml.cut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlAuthor extends AbstractXmlCutTest<Author>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAuthor.class);
	
	public TestXmlAuthor(){super(Author.class);}
	public static Author create(boolean withChildren){return (new TestXmlAuthor()).build(withChildren);}
    
    public Author build(boolean withChilds)
    {
    	Author xml = new Author();
    	xml.setValue("myAuthor");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlAuthor test = new TestXmlAuthor();
		test.saveReferenceXml();
    }
}