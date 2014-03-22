package net.sf.otrcutmp4.controller.cover;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.interfaces.controller.CoverManager;
import net.sf.otrcutmp4.model.xml.series.Season;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSystemWebCoverManager implements CoverManager
{
	final static Logger logger = LoggerFactory.getLogger(FileSystemWebCoverManager.class);
		
	private File coverDir;
	private FileSystemCoverManager cmFs;
	
	public FileSystemWebCoverManager(File coverDir)
	{
		this.coverDir=coverDir;
		logger.debug("Using FSW CoverManager");
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
		URL url;
		try {
			url = new URL(season.getImage().getUrl());
			FileUtils.copyURLToFile(url,fImage);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
}