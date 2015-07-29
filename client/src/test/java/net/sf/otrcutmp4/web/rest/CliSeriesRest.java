package net.sf.otrcutmp4.web.rest;

import java.io.File;
import java.io.IOException;

import net.sf.ahtutils.web.rest.auth.RestEasyPreemptiveClientExecutor;
import net.sf.exlp.interfaces.util.ConfigKey;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrClientTestBootstrap;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.cover.FileSystemWebCoverManager;
import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.factory.FileNameFactoy;
import de.kisner.otrcast.factory.txt.TxtDsFactory;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.interfaces.rest.OtrSeriesRest;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Tags;
import freemarker.template.TemplateException;

public class CliSeriesRest
{
	final static Logger logger = LoggerFactory.getLogger(CliSeriesRest.class);
	
	private Configuration config;
	private OtrSeriesRest rest;
	
	private String fnS = "12345_The_Big_Bang_Theory_11.12.15_12-50_pro7_25_TVOON_DE.mpg.HQ.cut.mp4";
	private String fnM = "12345_Game_Of_Thrones_Das_Lied_von_Eis_und_Feuer_12.03.23_20-15_rtl2_200_TVOON_DE.mpg.HQ.cut.mp4";
	
	private String template = "${seriesName} ${seasonNr}x${episodeNr} ${episodeName}";
	
	public CliSeriesRest(Configuration config)
	{	
		this.config=config;
		String url = config.getString(ConfigKey.netRestUrl);
		logger.info("Connection to "+url);
		
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory("user","pwd");
		rest = ProxyFactory.create(OtrSeriesRest.class, url,clientExecutor);
	}
	
	public void single() throws OtrProcessingException, IOException, TemplateException
	{
		JaxbUtil.debug(XmlOtrIdFactory.createForFileName(fnS));
		Tags tags = rest.getTags(fnS);
		JaxbUtil.debug(tags);
		
		TxtDsFactory fDs = new TxtDsFactory();
		FileNameFactoy fnf = new FileNameFactoy();
		fnf.initTemplate(template);
		
		logger.debug(fnf.convert(fDs.build(tags)));
	}
	
	public void multi() throws OtrProcessingException, IOException, TemplateException
	{
		JaxbUtil.debug(XmlOtrIdFactory.createForFileName(fnM));
		Tags tags = rest.getTags(fnM);
		JaxbUtil.debug(tags);
		
		TxtDsFactory fDs = new TxtDsFactory();
		FileNameFactoy fnf = new FileNameFactoy();
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
		Configuration config = OtrClientTestBootstrap.init();
		CliSeriesRest rest = new CliSeriesRest(config);
//		rest.single();
//		rest.multi();
//		rest.episode();
		rest.episodeWithCover();
	}
}