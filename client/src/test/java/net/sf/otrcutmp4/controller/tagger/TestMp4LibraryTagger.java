package net.sf.otrcutmp4.controller.tagger;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.processor.mc.McLibraryTagger;
import de.kisner.otrcast.interfaces.rest.OtrSeriesRest;
import de.kisner.otrcast.util.OtrBootstrap;

public class TestMp4LibraryTagger
{
	final static Logger logger = LoggerFactory.getLogger(TestMp4LibraryTagger.class);
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrBootstrap.init();

		File fLibrary = new File(config.getString("test.mp4Tagger.library"));
		File fBackup = new File(config.getString("test.mp4Tagger.backup"));
		
		String restUrl = config.getString("url.otrseries");
		logger.info("Connectiong to "+restUrl);
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(restUrl); 
		OtrSeriesRest rest = target.proxy(OtrSeriesRest.class);;
		
		McLibraryTagger tagger = new McLibraryTagger(rest,fBackup);
		tagger.scan(fLibrary);
	}
 }