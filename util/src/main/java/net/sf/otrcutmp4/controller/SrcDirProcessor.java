package net.sf.otrcutmp4.controller;

import java.io.File;

import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.controller.factory.xml.XmlOtrIdFactory;
import net.sf.otrcutmp4.controller.factory.xml.XmlOtrIdFactory.VideType;
import net.sf.otrcutmp4.controller.factory.xml.XmlVideoFileFactory;
import net.sf.otrcutmp4.interfaces.view.ViewInterface;
import net.sf.otrcutmp4.model.xml.cut.FileName;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.OtrId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SrcDirProcessor
{
	final static Logger logger = LoggerFactory.getLogger(SrcDirProcessor.class);
		
	private ViewInterface view;
	
	private File srcDir;
	private VideType vType;
	
	public SrcDirProcessor(ViewInterface view)
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
		
		StringBuffer sb = new StringBuffer();
		sb.append(result.getVideoFile().size());
		sb.append(" ").append(vType).append("file");
		if(result.getVideoFile().size()!=1){sb.append("s");}
		sb.append(" found.");
		logger.debug(sb.toString());
		return result;
	}
	
	private OtrId getFileId(FileName fName)
	{
		OtrId fId = null;
		switch(vType)
		{
			case avi: fId=getAviId(fName);break;
			case mp4: fId=getMp4Id(fName);break;
		}
		return fId;
	}
	
	private OtrId getAviId(FileName fName)
	{
		OtrId fId = new OtrId();
		
		String fileName = fName.getValue();
		fId.setKey(fileName.substring(0, fileName.lastIndexOf("."+XmlOtrIdFactory.typeAvi)));
		
		Format format = new Format();
		format.setType(XmlOtrIdFactory.typeAvi);
		fId.setFormat(format);
		
		return fId;
	}
	
	private OtrId getMp4Id(FileName fName)
	{
		OtrId fId = new OtrId();
		
		String fileName = fName.getValue();
		int indexFrom = fileName.indexOf("_")+1;
		int indexTo = fileName.lastIndexOf("."+XmlOtrIdFactory.typeMp4);
		fId.setKey(fileName.substring(indexFrom, indexTo));
		
		Format format = new Format();
		format.setType(XmlOtrIdFactory.typeMp4);
		fId.setFormat(format);
		
		return fId;
	}
}
