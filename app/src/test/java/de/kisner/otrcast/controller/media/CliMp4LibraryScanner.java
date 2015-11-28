package de.kisner.otrcast.controller.media;

import org.apache.commons.configuration.Configuration;

import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.tag.util.Mp4TestEnvironment;

public class CliMp4LibraryScanner
{
	public CliMp4LibraryScanner()
	{
		
	}
	
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCastBootstrap.init();
		
		Mp4LibraryScanner scanner = new Mp4LibraryScanner(OtrCastBootstrap.buildEmf(config).createEntityManager());
		scanner.scan(Mp4TestEnvironment.mp4Libaray(config));
	}
}