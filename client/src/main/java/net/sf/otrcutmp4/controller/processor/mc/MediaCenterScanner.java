package net.sf.otrcutmp4.controller.processor.mc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.otrcutmp4.controller.tag.Mp4TagReader;
import net.sf.otrcutmp4.model.OtrSeries;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.model.xml.series.Video;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MediaCenterScanner extends DirectoryWalker<File>
{
	final static Logger logger = LoggerFactory.getLogger(MediaCenterScanner.class);
	
	private Mp4TagReader tagReader;
	private UtilsFacadeBean ufb;
	private EntityManager em;
	
	public MediaCenterScanner(EntityManager em)
	{
		this.em=em;
		tagReader = new Mp4TagReader();
		ufb = new UtilsFacadeBean(em);
	}
	
	private static IOFileFilter filter()
	{
		IOFileFilter ioFfFile = FileFilterUtils.fileFileFilter();
		IOFileFilter ioFfMp4 = FileFilterUtils.suffixFileFilter("mp4");
		IOFileFilter ioFf1 = FileFilterUtils.and(ioFfFile,ioFfMp4);
		return ioFfMp4;
	}
	
	public void scan(File startDirectory)
	{
		DateTime startDate = new DateTime();
		List<File> results = new ArrayList<File>();
	    try
	    {
			walk(startDirectory, results);
		}
	    catch (IOException e) {e.printStackTrace();}
	    
	    Period period = new Period(startDate, new DateTime());
	    
	    logger.info("Processed "+results.size()+" files in "+PeriodFormat.getDefault().print(period));
	}

	@Override
	protected void handleFile(File file, int depth, Collection<File> results)
	{
		if(file.getAbsolutePath().endsWith(".mp4"))
		{
			logger.info("File: "+file.getAbsolutePath());
			try
			{
				Video video = tagReader.read(file);
				if(video.isSetEpisode()){handleEpisode(video.getEpisode());}
			}
			catch (IOException e) {e.printStackTrace();}
		    results.add(file);
		}
	}
	
	private void handleEpisode(Episode xmlEpisode)
	{
		OtrSeries series = getSeries(xmlEpisode.getSeason().getSeries());
		
	}
	
	private OtrSeries getSeries(Series xmlSeries)
	{
		OtrSeries series;
		try
		{
			series = ufb.fByName(OtrSeries.class, xmlSeries.getName());
		}
		catch (UtilsNotFoundException e)
		{
			series = new OtrSeries();
			series.setName(xmlSeries.getName());
	        em.getTransaction().begin();
	        em.persist(series);
	        em.getTransaction().commit();
		}
		return series;
	}
}