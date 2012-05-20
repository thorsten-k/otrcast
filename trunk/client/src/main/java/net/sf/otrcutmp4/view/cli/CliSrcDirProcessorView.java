package net.sf.otrcutmp4.view.cli;

import java.io.File;

import net.sf.otrcutmp4.interfaces.view.ViewSrcDirProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliSrcDirProcessorView implements ViewSrcDirProcessor
{
	final static Logger logger = LoggerFactory.getLogger(CliSrcDirProcessorView.class);
	
	@Override
	public void readFilesInDir(File srcDir)
	{
		logger.debug("Searching files in "+srcDir.getAbsolutePath());
	}

	@Override
	public void found(int i)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(i);
		sb.append(" ").append("file");
		if(i!=1){sb.append("s");}
		sb.append(" found.");
		logger.info(sb.toString());
	}

}
