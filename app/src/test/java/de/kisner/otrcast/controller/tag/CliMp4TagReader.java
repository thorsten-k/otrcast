package de.kisner.otrcast.controller.tag;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.tag.reader.Mp4TagReader;
import de.kisner.otrcast.factory.txt.TxtVideoFactory;
import de.kisner.otrcast.model.xml.video.Video;
import de.kisner.otrcast.util.query.io.FileQuery;

public class CliMp4TagReader
{
	final static Logger logger = LoggerFactory.getLogger(CliMp4TagReader.class);
	
	private Mp4TagReader tagReader;
	
	public CliMp4TagReader()
	{
		tagReader = new Mp4TagReader(false);
	}
		
	public void read(File f) throws IOException
	{
		logger.debug("Testing "+f.getAbsolutePath());
		Video video = tagReader.read(f);
		logger.info(TxtVideoFactory.build(video,true));
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrCastBootstrap.init();
		
		CliMp4TagReader test = new CliMp4TagReader();
		
		File srcMp4 = new File(config.getString("test.mp4Tagger.src"));
		File dstMp4 = new File(config.getString("test.mp4Tagger.dst"));
		
		logger.info("*************** Source Folder");
		for(File file : srcMp4.listFiles(FileQuery.mp4FileFilter()))
		{
			test.read(file);
		}
		
		logger.info("*************** Destination Folder");
		for(File file : dstMp4.listFiles(FileQuery.mp4FileFilter()))
		{
			test.read(file);
		}
	}
 }