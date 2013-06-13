package net.sf.otrcutmp4.controller.factory.xml.otr;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.controller.factory.xml.XmlFormatFactory;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.OtrId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlOtrIdFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlOtrIdFactory.class);
	
	public static enum VideType {avi,hq,hd,mp4}
	
	public static final String typeMp4 = "mpg.mp4";
	public static final String typeAviHq = "mpg.HQ.avi";
	public static final String typeAviHd = "mpg.HD.avi";
	public static final String typeAc3Hd = "mpg.HD.ac3";
	public static final String typeMp4Hq   = "mpg.HQ.cut.mp4";
	public static final String typeOtrkey   = "otrkey";
	
	private static final String tag = ".mpg.";
	
	public static OtrId createForId(String fileId)
	{		
		OtrId xml = new OtrId();
		
		int mpgIndex = fileId.indexOf(tag);
		
		xml.setKey(fileId.substring(0,mpgIndex));
		xml.setFormat(XmlFormatFactory.create(fileId.substring(mpgIndex+tag.length(),fileId.length())));
		
		return xml;
	}
	
	public static OtrId createForFileName(String fileName) throws OtrProcessingException
	{	
		OtrId xml = null;
		
		if(fileName.endsWith(typeOtrkey))
		{
			logger.warn("Is otrkey");
			int index = fileName.lastIndexOf(typeOtrkey);
			fileName = fileName.substring(0,index-1);
		}
		
		if(     fileName.endsWith(typeAviHq)){xml = getFileId(VideType.hq, fileName);}
		else if(fileName.endsWith(typeAviHd)){xml = getFileId(VideType.hd, fileName);}
		else if(fileName.endsWith(typeMp4Hq)){xml = getFileId(VideType.mp4, fileName);}
		else {throw new OtrProcessingException("Unknown video.type "+fileName);}
		
		return xml;
	}
	
	private static OtrId getFileId(VideType vType, String fileName)
	{
		OtrId otrId = null;
		
		switch(vType)
		{
			case hq: otrId=getHq(fileName);break;
			case hd: otrId=getHd(fileName);break;
			case mp4: otrId=getMp4Id(fileName);break;
		}
		return otrId;
	}
	
	private static OtrId getHq(String fileName)
	{
		OtrId fId = new OtrId();
		
		fId.setKey(fileName.substring(0, fileName.lastIndexOf("."+typeAviHq)));
		
		Format format = new Format();
		format.setType(typeAviHq);
		fId.setFormat(format);
		
		return fId;
	}
	
	private static OtrId getHd(String fileName)
	{
		OtrId fId = new OtrId();
		
		fId.setKey(fileName.substring(0, fileName.lastIndexOf("."+typeAviHd)));
		
		Format format = new Format();
		format.setType(typeAviHd);
		fId.setFormat(format);
		
		return fId;
	}
	
	private static OtrId getMp4Id(String fileName)
	{
		OtrId otrId = new OtrId();
		
		int indexFrom = fileName.indexOf("_")+1;
		int indexTo = fileName.lastIndexOf("."+typeMp4Hq);
		otrId.setOtrCl(fileName.substring(0,indexFrom-1));
		otrId.setKey(fileName.substring(indexFrom, indexTo));
		
		Format format = new Format();
		format.setType(typeMp4Hq);
		format.setAc3(false);
		otrId.setFormat(format);
		
		return otrId;
	}
	
	public static VideType getType(String s) throws UtilsProcessingException
	{
		if(s.equals(typeAviHq)){return VideType.hq;}
		else if(s.equals(typeAviHd)){return VideType.hd;}
		throw new UtilsProcessingException("No Type defined for "+s);
	}
}
