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
	
	protected File dirAvi,dirHqMp4;

	protected AviToMp4.Quality quality;
	protected AviToMp4.Audio audio;
	protected AviToMp4.Profile profile;
	
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
	
	public void init(AviToMp4.Quality quality, AviToMp4.Audio audio, AviToMp4.Profile profile) throws OtrInternalErrorException
	{
		this.quality=quality;
		this.audio=audio;
		this.profile=profile;
		
		switch(quality)
		{
			case HQ: dirAvi = new File(config.getString(OtrConfig.dirHqAvi));break;
			case HD: dirAvi = new File(config.getString(OtrConfig.dirHdAvi));break;
		}
		
		if(txt==null){throw new OtrInternalErrorException("txt not set");}
	}
	
	protected void setTxt(ExlpTxtWriter txt) {this.txt = txt;}
	
	protected String getRalative(File base, String file)
	{
		return rpf.relativate(new File(base,"raw_audio.mp3"));
	}
}
