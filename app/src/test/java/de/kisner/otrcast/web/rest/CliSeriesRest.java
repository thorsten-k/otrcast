package de.kisner.otrcast.web.rest;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.rest.OtrVideoRest;
import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.cover.FileSystemWebCoverManager;
import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.factory.txt.TxtDsFactory;
import de.kisner.otrcast.factory.txt.TxtFileNameFactoy;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Tags;
import freemarker.template.TemplateException;
import net.sf.exlp.interfaces.util.ConfigKey;
import net.sf.exlp.util.xml.JaxbUtil;

public class CliSeriesRest
{
	final static Logger logger = LoggerFactory.getLogger(CliSeriesRest.class);
	
	private Configuration config;
	private OtrVideoRest rest;
	
	private String fnS = "12345_The_Big_Bang_Theory_11.12.15_12-50_pro7_25_TVOON_DE.mpg.HQ.cut.mp4";
	private String fnM = "12345_Game_Of_Thrones_Das_Lied_von_Eis_und_Feuer_12.03.23_20-15_rtl2_200_TVOON_DE.mpg.HQ.cut.mp4";
	
	private String template = "${seriesName} ${seasonNr}x${episodeNr} ${episodeName}";
	
	public CliSeriesRest(Configuration config)
	{	
		this.config=config;
		String url = config.getString(ConfigKey.netRestUrl);
		logger.info("Connection to "+url);
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication("user","pwd"));
		ResteasyWebTarget restTarget = client.target(url);
		rest = restTarget.proxy(OtrVideoRest.class);
	}
	
	public void single() throws OtrProcessingException, IOException, TemplateException
	{
		JaxbUtil.debug(XmlOtrIdFactory.createForFileName(fnS));
		Tags tags = rest.getTags(fnS);
		JaxbUtil.debug(tags);
		
		TxtDsFactory fDs = new TxtDsFactory();
		TxtFileNameFactoy fnf = new TxtFileNameFactoy();
		fnf.initTemplate(template);
		
		logger.debug(fnf.convert(fDs.build(tags)));
	}
	
	public void multi() throws OtrProcessingException, IOException, TemplateException
	{
		JaxbUtil.debug(XmlOtrIdFactory.createForFileName(fnM));
		Tags tags = rest.getTags(fnM);
		JaxbUtil.debug(tags);
		
		TxtDsFactory fDs = new TxtDsFactory();
		TxtFileNameFactoy fnf = new TxtFileNameFactoy();
		fnf.initTemplate(template);
		
		logger.debug(fnf.convert(fDs.build(tags)));
	}
	
	public void episode()
	{
		Episode xml = rest.getEpisode(12);
		JaxbUtil.info(xml);
	}
	
	public void episodeWithCover()
	{
		Episode xml = rest.getEpisode(12);
		JaxbUtil.info(xml);
		File dCover = new File (config.getString("io.dir.cover"));
		FileSystemWebCoverManager fsw = new FileSystemWebCoverManager(dCover);
		logger.info("Cover available: "+fsw.isAvailable(xml.getSeason()));
	}
	
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCastBootstrap.init();
		CliSeriesRest rest = new CliSeriesRest(config);
//		rest.single();
//		rest.multi();
//		rest.episode();
		rest.episodeWithCover();
	}
}