package de.kisner.otrcast.controller.tag;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.AbstractOtrcastTest;
import de.kisner.otrcast.OtrUtilTestBootstrap;
import de.kisner.otrcast.controller.tag.util.Mp4BoxDebugger;
import de.kisner.otrcast.controller.tag.util.Mp4TestEnvironment;
import de.kisner.otrcast.util.query.io.FileQuery;

public class CliMp4BoxDebugger extends AbstractOtrcastTest
{
	final static Logger logger = LoggerFactory.getLogger(CliMp4BoxDebugger.class);
		
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrUtilTestBootstrap.init();
		
		File fMp4Library = Mp4TestEnvironment.mp4Libray(config);
		
		Mp4BoxDebugger debugger = new Mp4BoxDebugger();
		for(File f : fMp4Library.listFiles(FileQuery.mp4FileFilter()))
		{
			debugger.debug(f.getAbsolutePath());
		}
	}
 }