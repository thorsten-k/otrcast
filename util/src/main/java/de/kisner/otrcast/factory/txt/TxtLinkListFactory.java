package de.kisner.otrcast.factory.txt;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.factory.xml.XmlQualityFactory;
import de.kisner.otrcast.model.xml.otr.Format;
import de.kisner.otrcast.model.xml.otr.Linklist;
import de.kisner.otrcast.model.xml.otr.Quality;
import de.kisner.otrcast.model.xml.otr.Recording;

public class TxtLinkListFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtLinkListFactory.class);
	
	public TxtLinkListFactory()
	{
		
	}
	
	public List<String> create(Linklist xmlLinklist) throws OtrProcessingException
	{
		List<String> result = new ArrayList<String>();
		for(Recording recording : xmlLinklist.getRecording())
		{
			result.add(createOtrDownload(recording));
		}
		return result;
	}
	
	public String createOtrDownload(Recording recording) throws OtrProcessingException
	{
		checkXmlStructure(recording);
		
		StringBuffer sb = new StringBuffer();
		sb.append(recording.getLink().getUrl()).append("/");
		if(recording.getFormat().isCut()){sb.append(recording.getCutList().getId()).append("_");}
		sb.append(recording.getOtrId().getKey());
		sb.append(createSuffix(recording.getFormat(), recording.getFormat().getQuality()));
		
		return sb.toString();
	}
	
	protected void checkXmlStructure(Recording recording) throws OtrProcessingException
	{
		if(!recording.isSetFormat()){throw new OtrProcessingException("format not available");}
		if(!recording.getFormat().isSetType()){throw new OtrProcessingException("format @type not available");}
		if(!recording.getFormat().isSetCut()){throw new OtrProcessingException("format @cut not available");}
		if(!recording.getFormat().isSetOtrkey()){throw new OtrProcessingException("format @otrKey not available");}
		
		if(!recording.getFormat().isSetQuality()){throw new OtrProcessingException("format.quality not available");}
		if(!recording.getFormat().getQuality().isSetType()){throw new OtrProcessingException("format.quality.type not available");}
		
		if(!recording.isSetLink()){throw new OtrProcessingException("link not available");}
		if(!recording.getLink().isSetUrl()){throw new OtrProcessingException("link.url not available");}
		
		if(!recording.isSetOtrId()){throw new OtrProcessingException("otrId not available");}
		if(!recording.getOtrId().isSetKey()){throw new OtrProcessingException("otrId.key not available");}
		
		if(recording.getFormat().isSetCut() && recording.getFormat().isCut())
		{
			if(!recording.isSetCutList()){throw new OtrProcessingException("<cutList> not available, but recoring is a cut");}
			if(!recording.getCutList().isSetId()){throw new OtrProcessingException("<cutList> @id not available, but recoring is a cut");}
		}
	}
	
	protected static String createSuffix(Format format, Quality quality) throws OtrProcessingException
	{
		StringBuffer sb = new StringBuffer();
		sb.append(".mpg");
		
		if(quality.getType().equals(XmlQualityFactory.qHQ)){sb.append(".HQ");}
		
		if(format.isCut()){sb.append(".cut");}
		
		sb.append(".").append(format.getType());
		
		if(format.isOtrkey()){sb.append(".otrkey");}
		
		return sb.toString();
	}
}
