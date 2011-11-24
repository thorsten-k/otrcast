package net.sf.otrcutmp4.controller.batch;

import java.io.File;

import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;
import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;
import net.sf.otrcutmp4.util.OtrConfig.Tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AbstactBatchGenerator
{
	static Log logger = LogFactory.getLog(AbstactBatchGenerator.class);
	
	protected RelativePathFactory rpf;
	protected ExlpTxtWriter txt;
		
	protected String cmdMp4Box,cmdFfmpeg,cmdLame,cmdFaac;
	protected OtrConfig cfg;
	
	public AbstactBatchGenerator(OtrConfig cfg)
	{
		this.cfg=cfg;
		
		for(Dir dir : Dir.values())
		{
			logger.trace(dir+" "+cfg.getDir(dir));
		}
		
		rpf = new RelativePathFactory(cfg.getDir(Dir.BAT),RelativePathFactory.PathSeparator.CURRENT,true);
		
		cmdLame = rpf.relativate(new File(cfg.getDir(Dir.TOOLS),cfg.getTool(Tool.LAME)));
		cmdMp4Box = rpf.relativate(new File(cfg.getDir(Dir.TOOLS),cfg.getTool(Tool.MP4BOX)));
		cmdFfmpeg = rpf.relativate(new File(cfg.getDir(Dir.TOOLS),cfg.getTool(Tool.FFMPEG)));
		cmdFaac = rpf.relativate(new File(cfg.getDir(Dir.TOOLS),cfg.getTool(Tool.FAAC)));
	}
	
	protected File getAviDir(AviToMp4.Quality quality) throws OtrInternalErrorException
	{
		switch(quality)
		{
			case HQ: return cfg.getDir(Dir.HQAVI);
			case HD: return cfg.getDir(Dir.HDAVI);
		}
		throw new OtrInternalErrorException("No valid AVI Dir requested: quality="+quality);
	}
	
	protected void setTxt(ExlpTxtWriter txt) {this.txt = txt;}
}
