package net.sf.otrcutmp4.controller.batch;

import java.io.File;

import org.apache.commons.configuration.Configuration;

import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;
import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.util.OtrConfig;

public class AbstactBatchGenerator
{
	protected Configuration config;
	
	protected File dirAvi,dirTmp,dirHqMp4,dirTools,dirBat;

	protected AviToMp4.Quality quality;
	protected AviToMp4.Audio audio;
	protected AviToMp4.Profile profile;
	
	protected RelativePathFactory rpf;
	protected ExlpTxtWriter txt;
		
	protected String cmdMp4Box,cmdFfmpeg;
	
	public AbstactBatchGenerator(Configuration config)
	{
		this.config=config;
		rpf = new RelativePathFactory(true,false);
		
		dirTmp = new File(config.getString(OtrConfig.dirTmp));
		dirHqMp4 = new File(config.getString(OtrConfig.dirHqMp4));
		dirTools = new File(config.getString(OtrConfig.dirTools));
		dirBat = new File(config.getString(OtrConfig.dirBat));
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
		
		cmdMp4Box = rpf.relativate(dirBat, new File(dirTools,config.getString(OtrConfig.toolMp4Box)));
		cmdFfmpeg = rpf.relativate(dirBat, new File(dirTools,config.getString(OtrConfig.toolFfmpeg)));
	}
	
	protected void setTxt(ExlpTxtWriter txt) {this.txt = txt;}
}
