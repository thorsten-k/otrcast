package net.sf.otrcutmp4.controller.batch;

import java.io.File;

import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;
import net.sf.otrcutmp4.util.OtrConfig.Tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstactBatchGenerator
{
	final static Logger logger = LoggerFactory.getLogger(AbstactBatchGenerator.class);
	
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
	
	protected void setTxt(ExlpTxtWriter txt) {this.txt = txt;}
}
