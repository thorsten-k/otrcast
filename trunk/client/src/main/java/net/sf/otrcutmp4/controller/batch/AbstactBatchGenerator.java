package net.sf.otrcutmp4.controller.batch;

import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;
import net.sf.otrcutmp4.util.OtrConfig.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class AbstactBatchGenerator
{
	final static Logger logger = LoggerFactory.getLogger(AbstactBatchGenerator.class);
	
	protected RelativePathFactory rpf;

	protected String cmdMp4Box,cmdFfmpeg,cmdLame,cmdFaac,cmdEac3to,cmdNero;
	
	protected OtrConfig cfg;
	protected AviToMp4.Profile profile;
	
	public AbstactBatchGenerator(OtrConfig cfg, AviToMp4.Profile profile)
	{
		this.cfg=cfg;
		this.profile=profile;

		rpf = new RelativePathFactory(cfg.getDir(Dir.BAT),RelativePathFactory.PathSeparator.CURRENT,true);
		
		cmdLame = rpf.relativate(new File(cfg.getDir(Dir.TOOLS),cfg.getTool(Tool.LAME)));
		cmdMp4Box = rpf.relativate(new File(cfg.getDir(Dir.TOOLS),cfg.getTool(Tool.MP4BOX)));
		cmdFfmpeg = rpf.relativate(new File(cfg.getDir(Dir.TOOLS),cfg.getTool(Tool.FFMPEG)));
		cmdFaac = rpf.relativate(new File(cfg.getDir(Dir.TOOLS),cfg.getTool(Tool.FAAC)));
		cmdEac3to = rpf.relativate(new File(cfg.getDir(Dir.TOOLS),cfg.getTool(Tool.EAC3TO)));
		cmdNero = rpf.relativate(new File(cfg.getDir(Dir.TOOLS),cfg.getTool(Tool.NEROAAC)));
	}
}
