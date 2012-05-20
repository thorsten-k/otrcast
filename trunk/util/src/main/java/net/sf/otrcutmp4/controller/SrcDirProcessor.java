package net.sf.otrcutmp4.controller;

import java.io.File;

import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.controller.factory.xml.XmlOtrIdFactory.VideType;
import net.sf.otrcutmp4.controller.factory.xml.XmlVideoFileFactory;
import net.sf.otrcutmp4.interfaces.view.ViewSrcDirProcessor;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SrcDirProcessor
{
	final static Logger logger = LoggerFactory.getLogger(SrcDirProcessor.class);
		
	private ViewSrcDirProcessor view;
	
	private File srcDir;
	private VideType vType;
	
	public SrcDirProcessor(ViewSrcDirProcessor view)
	{
		this.view=view;
	}
	
	public VideoFiles readFiles(File srcDir, VideType vType)
	{
		this.srcDir = srcDir;
		this.vType=vType;
		
		return readFiles();
	}
	
	private VideoFiles readFiles()
	{
		view.readFilesInDir(srcDir);
		VideoFiles result = new VideoFiles();
		for(File f : srcDir.listFiles())
		{
			String fileName = f.getName();
			int index = fileName.lastIndexOf("."+vType);
			if(index>0)
			{
				try
				{
					VideoFile vf = XmlVideoFileFactory.create(f.getName());
					result.getVideoFile().add(vf);
				}
				catch (OtrProcessingException e) {logger.error("Error processing file: "+e.getMessage());}
			}
			else
			{
				logger.warn("File "+f.getName()+" is not a ."+vType+"-file. Ignoring");
			}
		}
		view.found(result.getVideoFile().size());
		return result;
	}
	

}
