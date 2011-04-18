package net.sf.otrcutmp4.batch;

import java.io.File;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;
import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;
import net.sf.exlp.util.os.shell.ShellCmdCopy;
import net.sf.otrcutmp4.data.jaxb.CutList;
import net.sf.otrcutmp4.data.jaxb.VideoFile;
import net.sf.otrcutmp4.data.jaxb.VideoFiles;
import net.sf.otrcutmp4.util.OtrConfig;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RenameGenerator
{
	static Log logger = LogFactory.getLog(RenameGenerator.class);
	
	private File dirHqMp4,dirBat,dirMp4Rename;
	private ExlpTxtWriter txt;
	
	private RelativePathFactory rpf;
	
	private ShellCmdCopy shellCopy;
	
	public RenameGenerator(Configuration config)
	{
		shellCopy = new ShellCmdCopy();
		
		dirHqMp4 = new File(config.getString(OtrConfig.dirHqMp4));
		dirBat = new File(config.getString(OtrConfig.dirBat));
		dirMp4Rename = new File(config.getString(OtrConfig.dirMp4Rename));
		
		logger.debug("");
		logger.debug("Creating Batch in "+dirBat.getAbsolutePath());
		
		txt = new ExlpTxtWriter();
		rpf = new RelativePathFactory();
	}
	
	public void create(VideoFiles vFiles)
	{
		 for(VideoFile vf : vFiles.getVideoFile())
		 {
			 crateForVideo(vf);
		 }

		 File f = new File(dirBat, "rename.bat");
		 txt.writeFile(f);
		 logger.info("");
		 logger.info("Batch file written to: "+rpf.relativate(new File("."), f));
	}
	
	private void crateForVideo(VideoFile vf)
	{	
		txt.add("echo Processing: "+vf.getFileName().getValue());
		String sOriginal = rpf.relativate(dirBat, new File(dirMp4Rename,vf.getFileName().getValue()));
		if(vf.isSetCutListsSelected() && vf.getCutListsSelected().isSetCutList())
		{
			for(CutList cl : vf.getCutListsSelected().getCutList())
			{
				cutList(cl, sOriginal);
			}
		}
	}
	
	private void cutList(CutList cl, String sOriginal)
	{
		String sTo = rpf.relativate(dirBat, new File(dirHqMp4,cl.getFileName().getValue()+".mp4"));
		
		try {txt.add(shellCopy.copyFile(sOriginal, sTo));}
		catch (ExlpUnsupportedOsException e)
		{
			logger.error(e);
			logger.error("File was not copied! ");
			logger.error("\tFrom: "+sOriginal);
			logger.error("\tTo  : "+sTo);
		}
	}
}