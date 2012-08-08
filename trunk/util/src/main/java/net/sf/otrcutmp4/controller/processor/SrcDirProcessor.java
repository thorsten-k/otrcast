package net.sf.otrcutmp4.controller.processor;

import java.io.File;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.controller.factory.xml.XmlVideoFileFactory;
import net.sf.otrcutmp4.controller.factory.xml.otr.XmlOtrIdFactory;
import net.sf.otrcutmp4.interfaces.view.ViewSrcDirProcessor;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SrcDirProcessor
{
	final static Logger logger = LoggerFactory.getLogger(SrcDirProcessor.class);
		
	private ViewSrcDirProcessor view;
	
	public SrcDirProcessor(ViewSrcDirProcessor view)
	{
		this.view=view;
	}
	
	public VideoFiles scan(File srcDir)
	{
		view.readFilesInDir(srcDir);
		VideoFiles vfs = privateScan(srcDir,false);
		view.found(vfs.getVideoFile().size());
		return vfs;
	}
	
	public VideoFiles scan(File srcDir, boolean recursive)
	{
		view.readFilesInDir(srcDir);
		VideoFiles vfs = privateScan(srcDir,recursive);
		view.found(vfs.getVideoFile().size());
		return vfs;
	}
	
	private VideoFiles privateScan(File srcDir,boolean rekursive)
	{
		VideoFiles result = new VideoFiles();
		for(File f : srcDir.listFiles())
		{
			boolean valid = isValidSrcFileName(f.getName());
			logger.trace("Testing: "+f.getName()+" valid?"+valid);
			if(valid)
			{
				try
				{
					VideoFile vf = XmlVideoFileFactory.create(f.getName());
					if(vf.getOtrId().getFormat().getType().equals(XmlOtrIdFactory.typeAviHd))
					{
						File fAc3 = new File(f.getParentFile(),vf.getOtrId().getKey()+"."+XmlOtrIdFactory.typeAc3Hd);
						vf.getOtrId().getFormat().setAc3(fAc3.exists());
					}
					result.getVideoFile().add(vf);
				}
				catch (OtrProcessingException e) {logger.error("Error processing file: "+e.getMessage());}
			}
			else if(f.isDirectory()){}
			else
			{
				if(!f.getName().endsWith(XmlOtrIdFactory.typeAc3Hd))
				{
					logger.debug("File "+f.getName()+" is not a valid source file. Ignoring it.");
				}
				
			}
		}
		if(rekursive)
		{
			for(File f : srcDir.listFiles())
			{
				if(f.isDirectory())
				{
					result.getVideoFile().addAll(privateScan(f,rekursive).getVideoFile());
				}
			}
		}
		JaxbUtil.trace(result);
		return result;
	}
	
	protected static boolean isValidSrcFileName(String fileName)
	{
		if(fileName.endsWith(XmlOtrIdFactory.typeAviHq)){return true;}
		if(fileName.endsWith(XmlOtrIdFactory.typeAviHd)){return true;}
		if(fileName.endsWith(XmlOtrIdFactory.typeMp4Hq)){return true;}
		return false;
	}
}
