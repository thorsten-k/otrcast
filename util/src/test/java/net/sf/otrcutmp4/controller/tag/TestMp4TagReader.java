package net.sf.otrcutmp4.controller.tag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.tag.reader.Mp4TagReader;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTestBootstrap;

import org.apache.commons.configuration.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMp4TagReader extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestMp4TagReader.class);
	
	public TestMp4TagReader(){}
	@Test public void dummy(){}
	
	@Before
	public void init()
	{
		tagReader = new Mp4TagReader(false);
	}
	
	private Mp4TagReader tagReader;
		
	public void read(String src) throws IOException
	{
		File f = new File(src);
		Video video = tagReader.read(f);
		JaxbUtil.info(video);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrUtilTestBootstrap.init();
		
		TestMp4TagReader test = new TestMp4TagReader();
		test.init();
		
		String src = config.getString("test.mp4Tagger.dst");
		logger.info("Using src dir: "+src);
		
		List<String> files = new ArrayList<String>();
		files.add("Movie-guess.mp4");
		files.add("Series-guess.mp4");
		files.add("Series-mt.mp4");
		files.clear();
		files.add("AviCutMp4.mp4");
		
		for(String file : files)
		{
			test.read(src+File.separator+file);
		}
	}
 }