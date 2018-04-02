package de.kisner.otrcast.controller.cover;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.cover.FileSystemCoverManager;
import de.kisner.otrcast.interfaces.controller.CoverManager.Format;
import de.kisner.otrcast.model.xml.video.tv.Season;
import de.kisner.otrcast.model.xml.video.tv.Series;
import de.kisner.otrcast.test.AbstractOtrcastTest;

public class TestFileSystemCoverManager extends AbstractOtrcastTest
{
	final static Logger logger = LoggerFactory.getLogger(TestFileSystemCoverManager.class);
	
	private File dirCover;
	private FileSystemCoverManager cmFs;
	
	private Season season;	
	
	@Before
	public void init() throws IOException
	{
		dirCover = new File(fTarget,"cover");
		if(!dirCover.exists()){dirCover.mkdir();}
		
		cmFs = new FileSystemCoverManager(dirCover);
		
		Series series = new Series();
		series.setKey("myKey");
		
		season = new Season();
		season.setNr(1);
		season.setSeries(series);
		
		File dirSeries = new File(dirCover,series.getKey());
		if(!dirSeries.exists()){dirSeries.mkdir();}
		
		File pngSeason1 = new File(dirSeries,"1.png");
		if(!pngSeason1.exists()){pngSeason1.createNewFile();}
		
		File jpgSeason2 = new File(dirSeries,"2.jpeg");
		if(!jpgSeason2.exists()){jpgSeason2.createNewFile();}
	}
	
	@After
	public void close()
	{
		if(dirCover.exists()){dirCover.delete();}
	}
	
	@Test
	public void seasonWithoutSeries()
	{
		season.setSeries(null);
		Assert.assertFalse(cmFs.isAvailable(season));
	}
	
	@Test
	public void seriesDirNotExisting()
	{
		season.getSeries().setKey("na");
		Assert.assertFalse(cmFs.isAvailable(season));
	}
	
	@Test
	public void seasonImageNotExisting()
	{
		season.setNr(0);
		Assert.assertFalse(cmFs.isAvailable(season));
	}
	
	@Test
	public void pngSeason1()
	{
		season.setNr(1);
		Assert.assertTrue(cmFs.isAvailable(season));
		Assert.assertEquals(Format.PNG, cmFs.getFormat());
	}
	
	@Test
	public void jpegSeason2()
	{
		season.setNr(2);
		Assert.assertTrue(cmFs.isAvailable(season));
		Assert.assertEquals(Format.JPEG, cmFs.getFormat());
	}
}