package net.sf.otrcutmp4.controller.tagger;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.otrcutmp4.controller.tag.Mp4LibraryTagger;
import net.sf.otrcutmp4.interfaces.rest.OtrSeriesRest;
import net.sf.otrcutmp4.util.OtrBootstrap;

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
		
		
		Mp4LibraryTagger tagger = new Mp4LibraryTagger(rest,fBackup);
		tagger.scan(fLibrary);
	}
 }