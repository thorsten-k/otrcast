package net.sf.otrcutmp4.view.cli;

import java.io.File;

import net.sf.otrcutmp4.view.interfaces.ViewInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliView implements ViewInterface
{
	final static Logger logger = LoggerFactory.getLogger(CliView.class);
	
	@Override
	public void readFilesInDir(File srcDir)
	{
		logger.debug("");
		logger.debug("Searching XXXXX TYP NYI"+" files in "+srcDir.getAbsolutePath());
	}

}