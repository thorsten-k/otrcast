package de.kisner.otrcast.controller.processor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.factory.xml.XmlVideoFileFactory;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import net.sf.exlp.util.xml.JaxbUtil;

public class OtrFileDownloader
{
	final static Logger logger = LoggerFactory.getLogger(OtrFileDownloader.class);
	private static enum Status {Pending,Queue,Finished}
	
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
		Status status = Status.Pending;
		
		while(status!=Status.Finished)
		{
			status = urlDownload(url,fileName);
		}

	}
	
	private Status urlDownload(URL url, String file) throws IOException
	{
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy", 3128));
		
		logger.info("Connecting");
		URLConnection conn = url.openConnection(proxy);
		conn.connect();
		long totalFileSize = conn.getContentLength();
		logger.info("Size: "+totalFileSize);
		 
		Map<String,List<String>> headerFields = conn.getHeaderFields();
		for(String key : headerFields.keySet())
		{
			StringBuffer sb = new StringBuffer();
			sb.append(key).append(": ");
			for(String s : headerFields.get(key))
			{
				sb.append(s).append(" -- ");
			}
			logger.trace(sb.toString());
		}
		if(headerFields.containsKey("X-OTR-Queueposition"))
		{
			logger.info("X-OTR-Queueposition :"+headerFields.get("X-OTR-Queueposition").get(0));
			try {Thread.sleep(31*1000);}
			catch (InterruptedException e) {e.printStackTrace();}
			return Status.Queue;
		}
		
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
	    return Status.Finished;
	}
}