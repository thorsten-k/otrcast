package de.kisner.otrcast.app;


import org.exlp.interfaces.system.property.Configuration;

import de.kisner.otrcast.controller.OtrCastBootstrap;

public class CliJettyServer
{
	public CliJettyServer(Configuration config)
	{
		
	}
	
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCastBootstrap.wrap();

		new CliJettyServer(config);
	}
}