package net.sf.otrcutmp4.xml.xpath;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.otr.Download;
import net.sf.otrcutmp4.model.xml.otr.Linklist;
import net.sf.otrcutmp4.model.xml.otr.OtrId;
import net.sf.otrcutmp4.model.xml.otr.Quality;
import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OtrXpath
{
	final static Logger logger = LoggerFactory.getLogger(OtrXpath.class);
	
	public static synchronized Download getDownload(List<Download> list, String type) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Linklist linklist = new Linklist();
		linklist.getDownload().addAll(list);
		return getDownload(linklist,type);
	}
	
	public static synchronized Download getDownload(Linklist linklist,String type) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(linklist);
		
		StringBuffer sb = new StringBuffer();
		sb.append("download[@type='"+type+"']");
		
		@SuppressWarnings("unchecked")
		List<Download> listResult = (List<Download>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Download.class.getSimpleName()+" for type="+type);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Download.class.getSimpleName()+" for type="+type);}
		return listResult.get(0);
	}
	
	public static synchronized OtrId getOtrIdByKey(Download download,String key) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(download);
		
		StringBuffer sb = new StringBuffer();
		sb.append("otrId[@key='"+key+"']");
		
		@SuppressWarnings("unchecked")
		List<OtrId> listResult = (List<OtrId>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+OtrId.class.getSimpleName()+" for key="+key);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+OtrId.class.getSimpleName()+" for key="+key);}
		return listResult.get(0);
	}
	
	public static synchronized Quality getQualityByKey(OtrId otrId,String type) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(otrId);
		
		StringBuffer sb = new StringBuffer();
		sb.append("quality[@type='"+type+"']");
		
		@SuppressWarnings("unchecked")
		List<Quality> listResult = (List<Quality>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Quality.class.getSimpleName()+" for type="+type);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Quality.class.getSimpleName()+" for type="+type);}
		return listResult.get(0);
	}
	
	public static synchronized VideoFile getFileByKey(VideoFiles vFiles,String key) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(vFiles);
		
		StringBuffer sb = new StringBuffer();
		sb.append("videoFile/otrId[@key='"+key+"']/..");
		
		@SuppressWarnings("unchecked")
		List<VideoFile> listResult = (List<VideoFile>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+OtrId.class.getSimpleName()+" for key="+key);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+OtrId.class.getSimpleName()+" for key="+key);}
		return listResult.get(0);
	}
}