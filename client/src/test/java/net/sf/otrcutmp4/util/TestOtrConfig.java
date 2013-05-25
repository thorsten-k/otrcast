package net.sf.otrcutmp4.util;

import java.io.File;
import java.io.IOException;

import net.sf.otrcutmp4.controller.exception.OtrConfigurationException;
import net.sf.otrcutmp4.test.AbstractClientTest;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOtrConfig extends AbstractClientTest
{ 
	final static Logger logger = LoggerFactory.getLogger(TestOtrConfig.class);
	
	public static final String faacKbit = "196";
	public static final String tpSeries = "template";
	public static final String cmdTagger = "java";
	
	private OtrConfig otrConfig;

	private static File fRoot,fAvi,fMp4,fTmp,fBat,fTools,fRename;
	private static File tMp4Box,tLame,tFfmpeg,tFaac,tEac3to,tNeroAac;
	
	public static TestOtrConfig factory() throws IOException
	{
		TestOtrConfig.initFiles();
		TestOtrConfig factory = new TestOtrConfig();
		factory.init();
		return factory;
	}
	
	@BeforeClass
	public static void initFiles() throws IOException
	{
		fRoot = new File("target/test");fRoot.mkdirs();
		fAvi = new File(fRoot,OtrConfig.dirAvi);fAvi.mkdirs();
		fMp4 = new File(fRoot,OtrConfig.dirMp4);fMp4.mkdirs();
		fTmp = new File(fRoot,OtrConfig.dirTmp);fTmp.mkdirs();
		fBat = fRoot;fBat.mkdirs();
		fTools = new File(fRoot,OtrConfig.dirTools);fTools.mkdirs();
		fRename = new File(fRoot,OtrConfig.dirRename);fRename.mkdirs();
		
		tMp4Box = new File(fTools,OtrConfig.toolMp4Box);tMp4Box.createNewFile();
		tLame = new File(fTools,OtrConfig.toolLame);tLame.createNewFile();
		tFfmpeg = new File(fTools,OtrConfig.toolFfmpeg);tFfmpeg.createNewFile();
		tFaac = new File(fTools,OtrConfig.toolFaac);tFaac.createNewFile();
		tEac3to = new File(fTools,OtrConfig.toolEac3to);tEac3to.createNewFile();
		tNeroAac = new File(fTools,OtrConfig.toolNeroAac);tNeroAac.createNewFile();
		
	}
	
	@Before
	public void init()
	{		
		Configuration config = new PropertiesConfiguration();
		
		config.addProperty(OtrConfig.dirAvi, fAvi.getAbsolutePath());
		config.addProperty(OtrConfig.dirMp4, fMp4.getAbsolutePath());
		config.addProperty(OtrConfig.dirTmp, fTmp.getAbsolutePath());
		config.addProperty(OtrConfig.dirBat, fBat.getAbsolutePath());
		config.addProperty(OtrConfig.dirTools, fTools.getAbsolutePath());
		config.addProperty(OtrConfig.dirRename, fRename.getAbsolutePath());
		
		config.addProperty(OtrConfig.toolMp4Box, tMp4Box.getName());
		config.addProperty(OtrConfig.toolLame, tLame.getName());
		config.addProperty(OtrConfig.toolFfmpeg, tFfmpeg.getName());
		config.addProperty(OtrConfig.toolFaac, tFaac.getName());
		config.addProperty(OtrConfig.toolEac3to, tEac3to.getName());
		config.addProperty(OtrConfig.toolNeroAac, tNeroAac.getName());
		
		config.addProperty(OtrConfig.paraAudioFaac, faacKbit);
		
		config.addProperty(OtrConfig.templateSeries, tpSeries);
		config.addProperty(OtrConfig.cmdTagger, cmdTagger);
		
		otrConfig = new OtrConfig(config);
	}
	
	public OtrConfig getOtrConfig() {return otrConfig;}
	
	@Test(expected=OtrConfigurationException.class)
	public void checkFail() throws OtrConfigurationException
	{
		otrConfig = new OtrConfig(new PropertiesConfiguration());
		otrConfig.checkConfigSettings();
	}
	
	@Test
	public void check() throws OtrConfigurationException
	{
		otrConfig.checkConfigSettings();
	}
}