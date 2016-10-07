package net.sf.otrcutmp4.test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;
import de.kisner.otrcast.util.OtrConfig;

public abstract class AbstractClientTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractClientTest.class);
	
	protected static File fRoot,fAvi,fMp4,fTmp,fBat,fTools,fCover,fMcIncoming,fMcMedia;
	
	protected static File fTarget;
	
	@BeforeClass
	public static void initFile()
	{
		if(!LoggerInit.isLog4jInited()){initLogger();}
		String dirTarget = System.getProperty("targetDir");
		if(dirTarget==null){dirTarget="target";}
		fTarget = new File(dirTarget);
		logger.debug("Using targeDir "+fTarget.getAbsolutePath());
	}
	
	@BeforeClass
	public static void prepareDirectories() throws IOException
	{
		fRoot = new File("target/test");fRoot.mkdirs();
		fAvi = new File(fRoot,OtrConfig.dirAvi);fAvi.mkdirs();
		fMp4 = new File(fRoot,OtrConfig.dirMp4);fMp4.mkdirs();
		fTmp = new File(fRoot,OtrConfig.dirTmp);fTmp.mkdirs();
		fBat = fRoot;fBat.mkdirs();
		fTools = new File(fRoot,OtrConfig.dirTools);fTools.mkdirs();
		fCover = new File(fRoot,OtrConfig.dirCover);fCover.mkdirs();
		fMcIncoming = new File(fRoot,OtrConfig.dirIncoming);fMcIncoming.mkdirs();
		fMcMedia = new File(fRoot,OtrConfig.dirMc);fMcMedia.mkdirs();
	}
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());
	}
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.addAltPath("otrcutmp4-client.test");
		loggerInit.init();
    }
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals(JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected static Date getDefaultDate()
	{
		return DateUtil.getDateFromInt(2011, 11, 11, 11, 11, 11);
	}
}