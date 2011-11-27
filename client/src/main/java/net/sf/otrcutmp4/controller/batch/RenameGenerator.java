package net.sf.otrcutmp4.controller.batch;

import java.io.File;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;
import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;
import net.sf.exlp.util.os.shell.ShellCmdMove;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RenameGenerator extends AbstactBatchGenerator
{
	final static Logger logger = LoggerFactory.getLogger(RenameGenerator.class);
	
	private File dirHqMp4,dirMp4Rename;
	private ExlpTxtWriter txt;
	
	private RelativePathFactory rpf;
	
	private ShellCmdMove shellMove;
	
	public RenameGenerator(OtrConfig cfg, Configuration config)
	{
		super(cfg);
		shellMove = new ShellCmdMove();
		
		dirHqMp4 = new File(config.getString(OtrConfig.dirMp4));
		dirMp4Rename = new File(config.getString(OtrConfig.dirRename));
		
		logger.debug("");
		logger.debug("Creating Batch in "+cfg.getDir(Dir.BAT));
		
		txt = new ExlpTxtWriter();
		rpf = new RelativePathFactory(RelativePathFactory.PathSeparator.CURRENT,true);
	}
	
	public void create(VideoFiles vFiles)
	{
		 for(VideoFile vf : vFiles.getVideoFile())
		 {
			 crateForVideo(vf);
		 }

		 File f = new File(cfg.getDir(Dir.BAT), "otrRename.bat");
		 txt.writeFile(f);
		 logger.info("");
		 logger.info("Batch file written to: "+rpf.relativate(new File("."), f));
	}
	
	private void crateForVideo(VideoFile vf)
	{	
		String sOriginal = rpf.relativate(cfg.getDir(Dir.BAT), new File(dirMp4Rename,vf.getFileName().getValue()));
		if(vf.isSetCutListsSelected() && vf.getCutListsSelected().isSetCutList())
		{
			txt.add("echo Processing: "+vf.getFileName().getValue());
			for(CutList cl : vf.getCutListsSelected().getCutList())
			{
				cutList(cl, sOriginal);
			}
		}
	}
	
	private void cutList(CutList cl, String sOriginal)
	{
		String sTo = rpf.relativate(cfg.getDir(Dir.BAT), new File(dirHqMp4,cl.getFileName().getValue()+".mp4"));
		
		try {txt.add(shellMove.moveFile(sOriginal, sTo));}
		catch (ExlpUnsupportedOsException e)
		{
			logger.error("",e);
			logger.error("File was not copied! ");
			logger.error("\tFrom: "+sOriginal);
			logger.error("\tTo  : "+sTo);
		}
	}
}