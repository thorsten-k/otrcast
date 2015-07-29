package de.kisner.otrcast.controller.tag;

import org.apache.commons.configuration.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.tag.writer.Mp4MediaTypeCorrector;
import de.kisner.otrcast.test.AbstractUtilTest;
import de.kisner.otrcast.test.OtrUtilTestBootstrap;

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
        mtc = new Mp4MediaTypeCorrector();
	}
	
	private Mp4MediaTypeCorrector mtc;
		
	public void read(String src) throws IOException
    {
        File f = new File(src);
        mtc.correct(f);
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