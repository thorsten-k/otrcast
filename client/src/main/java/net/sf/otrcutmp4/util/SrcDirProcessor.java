package net.sf.otrcutmp4.util;

import java.io.File;

import net.sf.otrcutmp4.model.xml.cut.FileId;
import net.sf.otrcutmp4.model.xml.cut.FileName;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SrcDirProcessor
{
	public static enum VideType {avi,mp4}
	
	static Log logger = LogFactory.getLog(SrcDirProcessor.class);
	
	private File srcDir;
	private VideType vType;
	
	public SrcDirProcessor()
	{
		
	}
	
	public VideoFiles readFiles(File srcDir, VideType vType)
	{
		this.srcDir = srcDir;
		this.vType=vType;
		
		logger.debug("");
		logger.debug("Searching "+vType+" files in "+srcDir.getAbsolutePath());
		return readFiles();
	}
	
	public VideoFiles readFiles()
	{
		VideoFiles result = new VideoFiles();
		for(File f : srcDir.listFiles())
		{
			String fileName = f.getName();
			int index = fileName.lastIndexOf("."+vType);
			if(index>0)
			{
				VideoFile vf = new VideoFile();
				
				FileName fName = new FileName();
				fName.setValue(f.getName());
				vf.setFileName(fName);
				
				FileId fId = getFileId(fName);
				vf.setFileId(fId);
				
				result.getVideoFile().add(vf);
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
	
	private FileId getFileId(FileName fName)
	{
		FileId fId = null;
		switch(vType)
		{
			case avi: fId=getAviId(fName);break;
			case mp4: fId=getMp4Id(fName);break;
		}
		return fId;
	}
	
	private FileId getAviId(FileName fName)
	{
		FileId fId = new FileId();
		
		String fileName = fName.getValue();
		fId.setValue(fileName.substring(0, fileName.lastIndexOf(".avi")));
		
		return fId;
	}
	
	private FileId getMp4Id(FileName fName)
	{
		FileId fId = new FileId();
		
		String fileName = fName.getValue();
		int indexFrom = fileName.indexOf("_")+1;
		int indexTo = fileName.lastIndexOf(".cut.mp4");
		fId.setValue(fileName.substring(indexFrom, indexTo));
		
		return fId;
	}
}
