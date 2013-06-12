package net.sf.otrcutmp4.controller.cover;

import java.io.File;

import net.sf.otrcutmp4.interfaces.controller.CoverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSystemCoverManager implements CoverManager
{
	final static Logger logger = LoggerFactory.getLogger(FileSystemCoverManager.class);
	
	private File coverDir;
	
	public FileSystemCoverManager(File coverDir)
	{
		this.coverDir=coverDir;
	}
		
}