package de.kisner.otrcast.controller.media;

import org.exlp.interfaces.system.property.Configuration;

import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.tag.util.Mp4TestEnvironment;

public class CliMp4LibraryScanner
{
	public CliMp4LibraryScanner()
	{
		
	}
	
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCastBootstrap.wrap();
		
		Mp4LibraryScanner scanner = new Mp4LibraryScanner(OtrCastBootstrap.buildEmf().createEntityManager());
		scanner.scan(Mp4TestEnvironment.mp4Libray(config));
	}
}