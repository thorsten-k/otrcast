package net.sf.otrcutmp4.controller.web;

import org.apache.commons.configuration.Configuration;

import de.kisner.otrcast.controller.OtrCutMp4Bootstrap;
import de.kisner.otrcast.controller.web.JettyServer;

public class CliJettyServer
{
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCutMp4Bootstrap.init();
		OtrCutMp4Bootstrap.buildEmf(config);
		new JettyServer();
	}
}