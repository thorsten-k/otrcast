package net.sf.otrcutmp4.controller.processor;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.controller.factory.xml.XmlVideoFileFactory;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;

import org.apache.commons.io.FileUtils;
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
		
		File f = new File(dirDownload,fileName+".otrkey");
		FileUtils.copyURLToFile(url, f);
	}
}