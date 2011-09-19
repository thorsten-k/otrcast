package net.sf.otrcutmp4.controller.processor.factory.txt;

import java.util.ArrayList;
import java.util.List;

import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.model.xml.otr.Recording;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TxtLinkListFactory
{
	static Log logger = LogFactory.getLog(TxtLinkListFactory.class);
	
	public TxtLinkListFactory()
	{
		
	}
	
	public List<String> create(String sLinkList)
	{
		List<String> result = new ArrayList<String>();
		
		return result;
	}
	
	public static String createOtrDownload(Recording recording) throws OtrProcessingException
	{
		checkXmlStructure(recording);
		
		StringBuffer sb = new StringBuffer();
		sb.append(recording.getLink().getUrl());
		
		return sb.toString();
	}
	
	private static void checkXmlStructure(Recording recording) throws OtrProcessingException
	{
		if(!recording.isSetFormat()){throw new OtrProcessingException("format not available");}
		if(!recording.getFormat().isSetType()){throw new OtrProcessingException("format.type not available");}
		
		if(!recording.getFormat().isSetQuality()){throw new OtrProcessingException("format.quality not available");}
		if(!recording.getFormat().getQuality().isSetType()){throw new OtrProcessingException("format.quality.type not available");}
		
		if(!recording.isSetLink()){throw new OtrProcessingException("link not available");}
		if(!recording.getLink().isSetUrl()){throw new OtrProcessingException("link.url not available");}
		
		if(!recording.isSetOtrId()){throw new OtrProcessingException("otrId not available");}
		if(!recording.getOtrId().isSetKey()){throw new OtrProcessingException("otrId.key not available");}
		
		if(recording.getFormat().isSetCut() && recording.getFormat().isCut())
		{
			
		}
	}
}
