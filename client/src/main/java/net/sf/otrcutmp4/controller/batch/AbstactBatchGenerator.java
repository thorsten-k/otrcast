package net.sf.otrcutmp4.controller.batch;

import java.io.File;

import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;
import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AbstactBatchGenerator
{
	static Log logger = LogFactory.getLog(AbstactBatchGenerator.class);
	
	protected Configuration config;
	
	protected File dirHqMp4;
	
	protected RelativePathFactory rpf;
	protected ExlpTxtWriter txt;
		
	protected String cmdMp4Box,cmdFfmpeg,cmdLame,cmdFaac;
	protected OtrConfig otrConfig;
	
	public AbstactBatchGenerator(OtrConfig otrConfig)
	{
		this.otrConfig=otrConfig;
		this.config=otrConfig.getConfig();
		
		for(Dir dir : Dir.values())
		{
			logger.trace(dir+" "+otrConfig.getDir(dir));
		}
		
		rpf = new RelativePathFactory(otrConfig.getDir(Dir.BAT),RelativePathFactory.PathSeparator.CURRENT,true);
		
		dirHqMp4 = new File(config.getString(OtrConfig.dirMp4));
		
		cmdLame = rpf.relativate(new File(otrConfig.getDir(Dir.TOOLS),config.getString(OtrConfig.toolLame)));
		cmdMp4Box = rpf.relativate(new File(otrConfig.getDir(Dir.TOOLS),config.getString(OtrConfig.toolMp4Box)));
		cmdFfmpeg = rpf.relativate(new File(otrConfig.getDir(Dir.TOOLS),config.getString(OtrConfig.toolFfmpeg)));
		cmdFaac = rpf.relativate(new File(otrConfig.getDir(Dir.TOOLS),config.getString(OtrConfig.toolFaac)));
	}
	
	protected File getAviDir(AviToMp4.Quality quality) throws OtrInternalErrorException
	{
		switch(quality)
		{
			case HQ: return otrConfig.getDir(Dir.HQAVI);
			case HD: return otrConfig.getDir(Dir.HDAVI);
		}
		throw new OtrInternalErrorException("No valid AVI Dir requested: quality="+quality);
	}
	
	protected void setTxt(ExlpTxtWriter txt) {this.txt = txt;}
}
