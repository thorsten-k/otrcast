package de.kisner.otrcast.app;

import org.apache.commons.configuration.Configuration;

import de.kisner.otrcast.controller.OtrCastBootstrap;

public class CliJettyServer
{
	public CliJettyServer(Configuration config)
	{
		
	}
	
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCastBootstrap.init();

		new CliJettyServer(config);
	}
}