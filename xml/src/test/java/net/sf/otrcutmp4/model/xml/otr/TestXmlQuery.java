package net.sf.otrcutmp4.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.cut.TestXmlVideoFile;
import net.sf.otrcutmp4.model.xml.mc.TestXmlImage;
import net.sf.otrcutmp4.model.xml.series.TestXmlEpisode;
import net.sf.otrcutmp4.model.xml.series.TestXmlMovie;
import net.sf.otrcutmp4.model.xml.series.TestXmlSeason;
import net.sf.otrcutmp4.model.xml.series.TestXmlSeries;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlQuery extends AbstractXmlOtrTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuery.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Query.class.getSimpleName()+".xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	Query test = create(true);
    	Query ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Query.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Query create(boolean withChilds)
    {
    	Query xml = new Query();    	
    	if(withChilds)
    	{
    		xml.setVideoFile(TestXmlVideoFile.create(false));
    		xml.setSeries(TestXmlSeries.create(false));
    		xml.setSeason(TestXmlSeason.create(false));
    		xml.setEpisode(TestXmlEpisode.create(false));
    		xml.setImage(TestXmlImage.create(false));
    		xml.setMovie(TestXmlMovie.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlQuery.initFiles();	
		TestXmlQuery test = new TestXmlQuery();
		test.save();
    }
}