package net.sf.otrcutmp4;

import java.io.File;

import net.sf.otrcutmp4.data.jaxb.Avi;
import net.sf.otrcutmp4.data.jaxb.VideoFile;
import net.sf.otrcutmp4.data.jaxb.VideoFiles;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AviProcessor
{
	static Log logger = LogFactory.getLog(AviProcessor.class);
	
	private File dirHqAvi;
	
	public AviProcessor(Configuration config)
	{
		dirHqAvi = new File(config.getString(OtrConfig.dirHqAvi));
		logger.debug("");
		logger.debug("Searching HQ.Avi files in "+dirHqAvi.getAbsolutePath());
	}
	
	public VideoFiles readFiles()
	{
		VideoFiles result = new VideoFiles();
		for(File f : dirHqAvi.listFiles())
		{
			Avi avi = new Avi();
			avi.setValue(f.getName());
			
			VideoFile vf = new VideoFile();
			vf.setAvi(avi);
			
			result.getVideoFile().add(vf);
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(result.getVideoFile().size()).append(" AVI file");
		if(result.getVideoFile().size()!=1){sb.append("s");}
		sb.append(" found.");
		logger.debug(sb.toString());
		return result;
	}
}
