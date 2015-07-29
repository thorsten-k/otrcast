package de.kisner.otrcast.controller.cover;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.controller.CoverManager;
import de.kisner.otrcast.model.xml.series.Season;
import de.kisner.otrcast.model.xml.series.Series;

public class FileSystemCoverManager implements CoverManager
{
	final static Logger logger = LoggerFactory.getLogger(FileSystemCoverManager.class);
	
	private File coverDir;
	private File fCover;
	private Format format;
	
	public FileSystemCoverManager(File coverDir)
	{
		this.coverDir=coverDir;
		logger.debug("Using FS CoverManager in "+coverDir.getAbsolutePath());
	}

	@Override
	public boolean isAvailable(Season season)
	{
		if(!season.isSetSeries())
		{
			logger.warn(Season.class.getSimpleName()+" does not have a "+Series.class.getSimpleName());
			return false;
		}
		
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

		File dir = new File(coverDir,season.getSeries().getKey());
		if(!dir.exists())
		{
			logger.warn("No cover for "+Season.class.getSimpleName()+" "+season.getSeries().getKey()+" and "+Season.class.getSimpleName()+" "+season.getSeries().getName());
			return false;
		}
		
		for(Format f : Format.values())
		{
			fCover = new File(dir,season.getNr()+"."+f.toString().toLowerCase());
			if(fCover.exists())
			{
				format = f;
				return true;
			}
		}
		logger.warn("No covers Season "+season.getNr());
		format = null;
		return false;
	}

	@Override
	public Format getFormat()
	{
		return format;
	}

	@Override
	public byte[] getImageStream() throws IOException
	{
		return FileUtils.readFileToByteArray(fCover);
	}
	
	public static Format toFormat(String fileName)
	{
		if(fileName.toLowerCase().endsWith(".png")){return Format.PNG;}
		else if(fileName.toLowerCase().endsWith(".jpeg")){return Format.JPEG;}
		else if(fileName.toLowerCase().endsWith(".jpg")){return Format.JPEG;}
		else
		{
			logger.warn("Unknown Image format: "+fileName);
			return null;
		}
	}
}