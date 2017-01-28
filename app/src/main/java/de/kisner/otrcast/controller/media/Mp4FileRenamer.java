package de.kisner.otrcast.controller.media;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.tag.reader.Mp4TagReader;
import de.kisner.otrcast.controller.tag.util.Mp4TestEnvironment;
import de.kisner.otrcast.model.xml.series.Video;
import de.kisner.otrcast.util.query.io.FileQuery;
import net.sf.exlp.util.io.StringUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class Mp4FileRenamer extends DirectoryWalker<File>
{
	final static Logger logger = LoggerFactory.getLogger(Mp4FileRenamer.class);
	
	private Mp4TagReader tagReader;
	
	public Mp4FileRenamer()
	{
		super(FileQuery.mp4FileFilter(),-1);
		tagReader = new Mp4TagReader(false);
	}
	
	public void rename(File srcDirectory, File dstDirectory)
	{
		List<File> results = new ArrayList<File>();
	    try {walk(srcDirectory, results);}
	    catch (IOException e) {e.printStackTrace();}
	    
	    logger.info(StringUtil.stars());
	    logger.info(results.size()+" files in "+srcDirectory.getAbsolutePath());
	    logger.info("Renamed files will be in "+dstDirectory.getAbsolutePath());
	    for(File f : results)
	    {
	    	try
	    	{
	    		renameFile(f,dstDirectory);
			}
	    	catch (IOException e) {e.printStackTrace();}
	    }
	    logger.info("File scanner finished with ");
	}

	@Override protected boolean handleDirectory(File directory, int depth, Collection<File> results) {return true;}
	@Override protected void handleFile(File file, int depth, Collection<File> results){results.add(file);}
	
	private void renameFile(File srcFile, File baseDir) throws IOException
	{
		Video v = tagReader.read(srcFile);
		JaxbUtil.info(v);

		File fOut = new File(baseDir,v.getEpisode().getSeason().getNr()+"x"+v.getEpisode().getNr()+" "+v.getEpisode().getName()+".mp4");

		InputStream in = new FileInputStream(srcFile);
		OutputStream out = new FileOutputStream(fOut);

		try
		{
			IOUtils.copy(in, out);
		}
		finally
		{
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCastBootstrap.init();
		
		Mp4FileRenamer scanner = new Mp4FileRenamer();
		scanner.rename(Mp4TestEnvironment.mp4RenameDirectorySrc(config),Mp4TestEnvironment.mp4RenameDirectoryDst(config));
	}
}