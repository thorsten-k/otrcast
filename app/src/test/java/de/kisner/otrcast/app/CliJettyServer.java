package de.kisner.otrcast.app;


import org.exlp.interfaces.system.property.Configuration;

import de.kisner.otrcast.util.OtrBootstrap;

public class CliJettyServer
{
	public CliJettyServer(Configuration config)
	{
		
	}
	
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrBootstrap.wrap();

		new CliJettyServer(config);
	}
}