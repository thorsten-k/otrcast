package net.sf.otrcutmp4.controller.web;

import org.apache.commons.configuration.Configuration;

import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.web.JettyServer;

public class CliJettyServer
{
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCastBootstrap.init();
		OtrCastBootstrap.buildEmf(config);
		new JettyServer();
	}
}