package de.kisner.otrcast.controller.tag;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.tag.util.Mp4BoxDebugger;
import de.kisner.otrcast.test.AbstractUtilTest;
import de.kisner.otrcast.test.OtrUtilTestBootstrap;
import de.kisner.otrcast.util.query.io.FileQuery;

public class CliMp4BoxDebugger extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(CliMp4BoxDebugger.class);
		
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrUtilTestBootstrap.init();
		
		File fDst = new File(config.getString(OtrUtilTestBootstrap.mp4TestDst));
		
		Mp4BoxDebugger debugger = new Mp4BoxDebugger();
		for(File f : fDst.listFiles(FileQuery.mp4FileFilter()))
		{
			debugger.debug(f.getAbsolutePath());
		}
	}
 }