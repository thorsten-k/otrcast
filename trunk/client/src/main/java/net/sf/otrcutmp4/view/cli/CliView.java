package net.sf.otrcutmp4.view.cli;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.otrcutmp4.controller.SrcDirProcessor;
import net.sf.otrcutmp4.view.interfaces.ViewInterface;

public class CliView implements ViewInterface
{
	static Log logger = LogFactory.getLog(SrcDirProcessor.class);
	
	@Override
	public void readFilesInDir(File srcDir)
	{
		logger.debug("");
		logger.debug("Searching XXXXX TYP NYI"+" files in "+srcDir.getAbsolutePath());
	}

}
