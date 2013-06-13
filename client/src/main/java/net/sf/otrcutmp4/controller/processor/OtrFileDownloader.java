package net.sf.otrcutmp4.controller.processor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.controller.factory.xml.XmlVideoFileFactory;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtrFileDownloader
{
	final static Logger logger = LoggerFactory.getLogger(OtrFileDownloader.class);
		
	private File dirDownload;
	private OtrKeyPreProcessor kpp;
	
	public OtrFileDownloader(File dirDownload)
	{
		this.dirDownload=dirDownload;
		kpp = new OtrKeyPreProcessor();
	}
	
	public void download(String line) throws OtrProcessingException, IOException
	{
		logger.info("Downloading "+line);
		
		String fileName = kpp.guess(line);
		logger.info("File : "+fileName);
		
		VideoFile vf = XmlVideoFileFactory.create(fileName);
		JaxbUtil.info(vf);
		URL url = new URL(line);
//		urlDownload(url);

	}
	
	private void urlDownload(URL url) throws IOException
	{
		logger.info("Connecting");
		URLConnection conn = url.openConnection();
		conn.connect();
		long totalFileSize = conn.getContentLength();
		logger.info("Size: "+totalFileSize);
		 
		BufferedInputStream in = null;
		FileOutputStream fout = null;
	    try
	    {
	    	in = new BufferedInputStream(conn.getInputStream());
	    	fout = new FileOutputStream(new File(dirDownload,"test.io"));
		 
	    	byte data[] = new byte[1024];
	    	int count;
	    	while ((count = in.read(data, 0, 1024)) != -1)
	    	{
	    		fout.write(data, 0, count);
	    	}
	    }
		finally
	    {
			if (in != null){in.close();}
	    	if (fout != null){fout.close();}
	    }
	}
}