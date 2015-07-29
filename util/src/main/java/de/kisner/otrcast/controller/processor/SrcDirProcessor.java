package de.kisner.otrcast.controller.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.factory.xml.XmlVideoFileFactory;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.interfaces.view.ViewSrcDirProcessor;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;

public class SrcDirProcessor
{
	final static Logger logger = LoggerFactory.getLogger(SrcDirProcessor.class);
		
	private ViewSrcDirProcessor view;
	
	private List<String> validFileSuffix;
	
	public SrcDirProcessor(ViewSrcDirProcessor view)
	{
		this.view=view;
		validFileSuffix = new ArrayList<String>();
		validFileSuffix.add(XmlOtrIdFactory.typeAviHq);
		validFileSuffix.add(XmlOtrIdFactory.typeAviHd);
		validFileSuffix.add(XmlOtrIdFactory.typeMp4Hq);
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
			if(f.isFile())
			{
				String fileName = f.getName();
				
				boolean valid = isValidSrcFileName(fileName);
				logger.trace("Testing: "+f.getName()+" valid?"+valid);
				if(valid)
				{
					try
					{
						VideoFile vf = XmlVideoFileFactory.create(fileName);
						if(vf.getOtrId().getFormat().getType().equals(XmlOtrIdFactory.typeAviHd))
						{
							File fAc3 = new File(f.getParentFile(),vf.getOtrId().getKey()+"."+XmlOtrIdFactory.typeAc3Hd);
							vf.getOtrId().getFormat().setAc3(fAc3.exists());
						}
						result.getVideoFile().add(vf);
					}
					catch (OtrProcessingException e) {logger.error("Error processing file: "+e.getMessage());}
				}
				else
				{
					if(!f.getName().endsWith(XmlOtrIdFactory.typeAc3Hd))
					{
						logger.trace("File "+f.getName()+" is not a valid source file. Ignoring it.");
					}
					
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
	
	protected boolean isValidSrcFileName(String fileName)
	{
		for(String suffix : validFileSuffix)
		{
			if(fileName.endsWith(suffix)){return true;}
		}
		return false;
	}
	
	public void addValidSuffix(String suffix)
	{
		validFileSuffix.add(suffix);
	}
}