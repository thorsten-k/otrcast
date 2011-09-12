package net.sf.otrcutmp4.model.xml.xpath;

import java.util.List;

import net.sf.ahtutils.xml.access.RoleAutoAssign;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.otrcutmp4.model.xml.otr.Download;
import net.sf.otrcutmp4.model.xml.otr.Linklist;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OtrXpath
{
	static Log logger = LogFactory.getLog(OtrXpath.class);
	
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
}