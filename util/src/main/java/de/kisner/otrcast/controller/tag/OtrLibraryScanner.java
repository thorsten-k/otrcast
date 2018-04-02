package de.kisner.otrcast.controller.tag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.DirectoryWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.tag.reader.Mp4TagReader;
import de.kisner.otrcast.factory.xml.video.XmlVideosFactory;
import de.kisner.otrcast.model.xml.series.Video;
import de.kisner.otrcast.model.xml.series.Videos;
import de.kisner.otrcast.util.query.io.FileQuery;
import net.sf.exlp.util.io.StringUtil;

public class OtrLibraryScanner extends DirectoryWalker<File>
{
	final static Logger logger = LoggerFactory.getLogger(OtrLibraryScanner.class);
	
	private Mp4TagReader tagReader;
		
	public OtrLibraryScanner()
	{
		super(FileQuery.mp4FileFilter(),-1);
	
		tagReader = new Mp4TagReader(true);
	}
	
	public Videos scan(File startDirectory)
	{
		List<File> results = new ArrayList<File>();
	    try {walk(startDirectory, results);}
	    catch (IOException e) {e.printStackTrace();}
	    
	    Videos videos = XmlVideosFactory.build();
	    logger.info(StringUtil.stars());
	    logger.info("File scanner will start with "+results.size()+" files");
	    for(File f : results)
	    {
	    	try
	    	{
	    		videos.getVideo().add(scanFile(f));
			}
	    	catch (IOException e) {e.printStackTrace();}
	    }
	    return videos;
	}

	@Override protected boolean handleDirectory(File directory, int depth, Collection<File> results) {return true;}
	@Override protected void handleFile(File file, int depth, Collection<File> results){results.add(file);}
	
	private Video scanFile(File file) throws IOException
	{
		Video video = tagReader.read(file);
		if(video.isSetEpisode()) {video.getEpisode().setImage(null);}
		return video;
	}	
}