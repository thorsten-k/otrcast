package net.sf.otrcutmp4.web.rest;

import java.io.IOException;

import net.sf.ahtutils.web.rest.RestEasyPreemptiveClientExecutor;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.controller.factory.FileNameFactoy;
import net.sf.otrcutmp4.controller.factory.txt.TxtDsFactory;
import net.sf.otrcutmp4.controller.factory.xml.otr.XmlOtrIdFactory;
import net.sf.otrcutmp4.interfaces.rest.OtrSeriesRest;
import net.sf.otrcutmp4.model.xml.series.Tags;
import net.sf.otrcutmp4.test.OtrClientTstBootstrap;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;

public class TestSeriesRest
{
	final static Logger logger = LoggerFactory.getLogger(TestSeriesRest.class);
	
	private OtrSeriesRest rest;
	
	private String fnS = "12345_The_Big_Bang_Theory_11.12.15_12-50_pro7_25_TVOON_DE.mpg.HQ.cut.mp4";
	private String fnM = "12345_Game_Of_Thrones_Das_Lied_von_Eis_und_Feuer_12.03.23_20-15_rtl2_200_TVOON_DE.mpg.HQ.cut.mp4";
	
	private String template = "${seriesName} ${seasonNr}x${episodeNr} ${episodeName}";
	
	public TestSeriesRest()
	{	
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory("user","pwd");
		rest = ProxyFactory.create(OtrSeriesRest.class, "http://localhost:8080/otr",clientExecutor);
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
	
	public static void main(String[] args) throws Exception
	{
		OtrClientTstBootstrap.init();
		TestSeriesRest rest = new TestSeriesRest();
		rest.single();
		rest.multi();
	}
}