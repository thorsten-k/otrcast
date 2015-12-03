package de.kisner.otrcast.controller.tag.util;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.tag.CliMp4TagWriter;
import de.kisner.otrcast.interfaces.controller.TestPropertyKeys;
import de.kisner.otrcast.util.query.io.FileQuery;

public class Mp4TestEnvironment
{
	final static Logger logger = LoggerFactory.getLogger(Mp4TestEnvironment.class);
	
	public static File mp4Libray(Configuration config)
	{
		File fMp4 = new File(config.getString(TestPropertyKeys.dirTaggerDst));
		File[] files = fMp4.listFiles(FileQuery.mp4FileFilter());
		if(files.length==0)
		{
			logger.warn("No Files in directory "+fMp4.getAbsolutePath());
			logger.info("Probably you need to create some test files with "+CliMp4TagWriter.class.getSimpleName());
		}
		return fMp4;
	}
}