package de.kisner.otrcast.controller.cover;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.controller.CoverManager;
import de.kisner.otrcast.model.xml.series.Season;
import de.kisner.otrcast.web.RedirectDownloader;
import net.sf.exlp.util.xml.JaxbUtil;

public class FileSystemWebCoverManager implements CoverManager
{
	final static Logger logger = LoggerFactory.getLogger(FileSystemWebCoverManager.class);
		
	private File coverDir;
	private FileSystemCoverManager cmFs;
	
	public FileSystemWebCoverManager(File coverDir)
	{
		this.coverDir=coverDir;
		logger.info("Using FSW CoverManager");
		cmFs = new FileSystemCoverManager(coverDir);
	}

	@Override
	public boolean isAvailable(Season season)
	{
		if(season.isSetImage() && !cmFs.isAvailable(season))
		{
			downloadCover(season);
		}
		return cmFs.isAvailable(season);
	}

	@Override public Format getFormat() {return cmFs.getFormat();}
	@Override public byte[] getImageStream() throws IOException {return cmFs.getImageStream();}
	
	private void downloadCover(Season season)
	{
		JaxbUtil.info(season.getImage());
		if(season.getImage().isSetUrl()){downloadCoverUrl(season);}
	}
	
	private void downloadCoverUrl(Season season)
	{
		File dir = new File(coverDir,season.getSeries().getKey());;
		if(!dir.exists()){dir.mkdir();}
		File fImage = new File(dir,season.getNr()+"."+FileSystemCoverManager.toFormat(season.getImage().getUrl()).toString().toLowerCase());
		logger.info("Downloading "+season.getImage().getUrl());
		logger.info("Target file: "+fImage.getAbsolutePath());
		try
		{
			URL url = new URL(season.getImage().getUrl());		
			RedirectDownloader downloader = new RedirectDownloader();
			downloader.download(url,fImage);
			
		}
		catch (MalformedURLException e) {e.printStackTrace();}
	}	
}