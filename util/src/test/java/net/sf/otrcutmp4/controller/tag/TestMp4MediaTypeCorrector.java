package net.sf.otrcutmp4.controller.tag;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.otrcutmp4.controller.tag.reader.Mp4TagReader;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTestBootstrap;
import org.apache.commons.configuration.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestMp4MediaTypeCorrector extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestMp4MediaTypeCorrector.class);

	public TestMp4MediaTypeCorrector(){}
	@Test public void dummy(){}
	
	@Before
	public void init()
	{
		tagReader = new Mp4TagReader(false);
	}
	
	private Mp4TagReader tagReader;
		
	public void read(String src) throws IOException
    {
        try
        {
            File f = new File(src);
            Mp4BoxManager.Type type = tagReader.readMediaType(f);
        }
        catch (UtilsNotFoundException e)
        {
            logger.info("MediaType not set");
        }
    }
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrUtilTestBootstrap.init();
		
		TestMp4MediaTypeCorrector test = new TestMp4MediaTypeCorrector();
		test.init();
		
		String src = config.getString("test.mp4Tagger.dst");
		logger.info("Using src dir: "+src);
		
		List<String> files = new ArrayList<String>();
		files.add("AviCutMp4.mp4");
		
		for(String file : files)
		{
			test.read(src+File.separator+file);
		}
	}
 }