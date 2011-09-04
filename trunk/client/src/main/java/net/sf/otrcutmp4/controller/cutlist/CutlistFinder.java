package net.sf.otrcutmp4.controller.cutlist;

import java.util.List;
import java.util.NoSuchElementException;

import net.sf.exlp.event.handler.EhResultContainer;
import net.sf.exlp.event.impl.JDomEvent;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerHttp;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.parser.impl.XmlParser;
import net.sf.otrcutmp4.model.xml.cut.Author;
import net.sf.otrcutmp4.model.xml.cut.Comment;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.CutListsAvailable;
import net.sf.otrcutmp4.model.xml.cut.FileName;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

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
		logger.info(" ");
		for(VideoFile vf : vFiles.getVideoFile())
		{
			searchCutlist(vf);
		}
		return vFiles;
	}
	
	private void searchCutlist(VideoFile vf)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(vf.getOtrId().getName()).append(".").append(vf.getOtrId().getType());
		logger.info("Searching for "+sb);
		String sUrl = "http://cutlist.at/getxml.php?name="+sb;
		
		EhResultContainer leh = new EhResultContainer();
		LogParser lp = new XmlParser(leh);
		
		LogListener ll = new LogListenerHttp(lp);
		ll.processMulti(sUrl);
		
		try
		{
			Document doc = ((JDomEvent)leh.getSingleResult()).getDoc();
			vf.setCutListsAvailable(getAvailableCutLists(doc));
		}
		catch (NoSuchElementException e){logger.error("No cutlist found. URL: "+sUrl);}
	}
	
	@SuppressWarnings("unchecked")
	private CutListsAvailable getAvailableCutLists(Document doc)
	{
		CutListsAvailable cls = new CutListsAvailable();
		if(doc!=null)
		{
			try
			{
				XPath xpath = XPath.newInstance("/files/cutlist");
				List<Object> lCl= xpath.selectNodes(doc);
				for(Object cl : lCl)
				{
					Element e = (Element)cl;
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