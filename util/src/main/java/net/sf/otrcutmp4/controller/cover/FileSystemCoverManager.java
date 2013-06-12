package net.sf.otrcutmp4.controller.cover;

import java.io.File;
import java.io.IOException;

import net.sf.otrcutmp4.interfaces.controller.CoverManager;
import net.sf.otrcutmp4.model.xml.series.Season;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSystemCoverManager implements CoverManager
{
	final static Logger logger = LoggerFactory.getLogger(FileSystemCoverManager.class);
	
	private File coverDir;
	private File fCover;
	
	public FileSystemCoverManager(File coverDir)
	{
		this.coverDir=coverDir;
	}

	@Override
	public boolean isAvailable(Season season)
	{
		if(!coverDir.exists())
		{
			logger.warn("Cover folder does not exist: "+coverDir.getAbsolutePath());
			return false;
		}
		if(!coverDir.isDirectory())
		{
			logger.warn("Not a directory: "+coverDir.getAbsolutePath());
			return false;
		}

		File dirSeason = new File(coverDir,season.getSeries().getKey());
		if(!dirSeason.exists()){return false;}
		
		fCover = new File(dirSeason,season.getNr()+".png");
		if(!fCover.exists()){return false;}
		
		
		return true;
	}

	@Override
	public Format getFormat()
	{
		return Format.PNG;
	}

	@Override
	public byte[] getImageStream() throws IOException
	{
		return FileUtils.readFileToByteArray(fCover);
	}
		
}