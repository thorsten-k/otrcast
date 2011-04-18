package net.sf.otrcutmp4.cutlist;

import java.util.List;

import net.sf.exlp.event.handler.EhResultContainer;
import net.sf.exlp.event.impl.JDomEvent;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerHttp;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.parser.impl.XmlParser;
import net.sf.otrcutmp4.data.jaxb.Author;
import net.sf.otrcutmp4.data.jaxb.Comment;
import net.sf.otrcutmp4.data.jaxb.CutList;
import net.sf.otrcutmp4.data.jaxb.CutListsAvailable;
import net.sf.otrcutmp4.data.jaxb.FileName;
import net.sf.otrcutmp4.data.jaxb.VideoFile;
import net.sf.otrcutmp4.data.jaxb.VideoFiles;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;


public class CutlistFinder
{
	static Log logger = LogFactory.getLog(CutlistFinder.class);
		
	public CutlistFinder()
	{

	}
	
	public VideoFiles searchCutlist(VideoFiles vFiles)
	{
		for(VideoFile vf : vFiles.getVideoFile())
		{
			searchCutlist(vf);
		}
		return vFiles;
	}
	
	private void searchCutlist(VideoFile vf)
	{
		String fileName = vf.getAvi().getValue();
		
		fileName = fileName.substring(0, fileName.lastIndexOf(".avi"));
		
		logger.info(" ");
		logger.info("Searching for "+fileName);
		
		EhResultContainer leh = new EhResultContainer();
		LogParser lp = new XmlParser(leh);
		
		LogListener ll = new LogListenerHttp(lp);
		ll.processMulti("http://cutlist.at/getxml.php?name="+fileName);
		
		Document doc = ((JDomEvent)leh.getSingleResult()).getDoc();
		vf.setCutListsAvailable(getAvailableCutLists(doc));
	}
	
	@SuppressWarnings("unchecked")
	private CutListsAvailable getAvailableCutLists(Document doc)
	{
		CutListsAvailable cls = new CutListsAvailable();
		if(doc!=null)
		{
			try
			{
				XPath xpath = XPath.newInstance( "/files/cutlist" );
				List<Object> lCl= xpath.selectNodes(doc);
				for(Object cl : lCl)
				{
					Element e = (Element)cl;
//					JDomUtil.debug(e);
					cls.getCutList().add(getCutList(e));
				}
			}
			catch (JDOMException e) {logger.error(e);}
		}
		return cls;
	}
	
	private CutList getCutList(Element e)
	{
		CutList cl = new CutList();
		
		cl.setId(e.getChildTextNormalize("id"));
		
		String sRating = e.getChildTextNormalize("rating");
		if(sRating !=null && sRating.length()>0){cl.setRating(new Double(sRating));}
		
		cl.setRatingCount(new Integer(e.getChildTextNormalize("ratingcount")));
		
		Author author = new Author();
		author.setValue(e.getChildTextNormalize("author"));
		cl.setAuthor(author);
		
		Comment comment = new Comment();
		comment.setValue(e.getChildTextNormalize("usercomment"));
		cl.setComment(comment);
		
		FileName fn = new FileName();
		fn.setValue(e.getChildTextNormalize("filename"));
		cl.setFileName(fn);
		
		
		return cl;
	}
}