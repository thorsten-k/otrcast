package de.kisner.otrcast.controller.tag;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.tag.util.Mp4BoxDebugger;
import de.kisner.otrcast.interfaces.controller.TestPropertyKeys;
import de.kisner.otrcast.test.AbstractUtilTest;
import de.kisner.otrcast.test.OtrUtilTestBootstrap;
import de.kisner.otrcast.util.query.io.FileQuery;

public class CliMp4BoxDebugger extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(CliMp4BoxDebugger.class);
		
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrUtilTestBootstrap.init();
		
		File fDst = new File(config.getString(TestPropertyKeys.dirTaggerDst));
		
		Mp4BoxDebugger debugger = new Mp4BoxDebugger();
		File[] files = fDst.listFiles(FileQuery.mp4FileFilter());
		if(files.length==0)
		{
			logger.warn("No Files in directory "+fDst.getAbsolutePath());
			logger.info("Probably you need to create some test files with "+CliMp4TagWriter.class.getSimpleName());
		}
		
		for(File f : files)
		{
			debugger.debug(f.getAbsolutePath());
		}
	}
 }