package de.kisner.otrcast.app;

import org.apache.commons.configuration.Configuration;

import de.kisner.otrcast.controller.OtrCastBootstrap;

public class CliJettyServer
{
	public CliJettyServer()
	{
		
	}
	
	
	
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCastBootstrap.init();
//		OtrCastBootstrap.buildEmf(config);
//		new OtrCastServer();
	}
}