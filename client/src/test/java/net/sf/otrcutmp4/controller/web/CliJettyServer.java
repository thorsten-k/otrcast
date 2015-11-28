package net.sf.otrcutmp4.controller.web;

import org.apache.commons.configuration.Configuration;

import de.kisner.otrcast.app.OtrCastServer;
import de.kisner.otrcast.controller.OtrCastBootstrap;

public class CliJettyServer
{
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCastBootstrap.init();
		OtrCastBootstrap.buildEmf(config);
		new OtrCastServer();
	}
}