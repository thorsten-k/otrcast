package net.sf.otrcutmp4.controller.web;

import org.apache.commons.configuration.Configuration;

import net.sf.otrcutmp4.controller.OtrCutMp4Bootstrap;

public class CliJettyServer
{
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCutMp4Bootstrap.init();
		OtrCutMp4Bootstrap.buildEmf(config);
		new JettyServer();
	}
}