package net.sf.otrcutmp4.controller.cutlist;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import net.sf.exlp.event.handler.EhResultContainer;
import net.sf.exlp.event.impl.JDomEvent;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerHttp;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.parser.impl.XmlParser;
import net.sf.otrcutmp4.controller.factory.xml.XmlOtrIdFactory;
import net.sf.otrcutmp4.model.xml.cut.Author;
import net.sf.otrcutmp4.model.xml.cut.Comment;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.CutListsAvailable;
import net.sf.otrcutmp4.model.xml.cut.FileName;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CutlistFinder
{
	final static Logger logger = LoggerFactory.getLogger(CutlistFinder.class);
		
	public CutlistFinder()
	{

	}
	
	public VideoFiles searchCutlist(VideoFiles vFiles)
	{
		logger.info(" ");
		for(VideoFile vf : vFiles.getVideoFile())
		{
			CutListsAvailable avlCutLists = searchCutlist(vf);
			if(avlCutLists.getCutList().size()>0){vf.setCutListsAvailable(avlCutLists);}
		}
		return vFiles;
	}
	
	public CutListsAvailable searchCutlist(VideoFile vf)
	{
		CutListsAvailable result = new CutListsAvailable();
		
		logger.info("Searching for "+vf.getOtrId().getKey()+"."+vf.getOtrId().getFormat().getType());
		StringBuffer sb = new StringBuffer();
		sb.append(vf.getOtrId().getKey()).append(".").append(vf.getOtrId().getFormat().getType());
		
		Map<String,CutList> mapCl = new Hashtable<String,CutList>();
		
		for(CutList cl : find(vf.getOtrId().getKey()+"."+vf.getOtrId().getFormat().getType()).getCutList())
		{
			mapCl.put(cl.getId(), cl);
		}
		
		if(XmlOtrIdFactory.typeAvi.equals(vf.getOtrId().getFormat().getType()))
		{
			for(CutList cl : find(vf.getOtrId().getKey()+".mpg.HQ").getCutList())
			{
				mapCl.put(cl.getId(), cl);
			}
		}
		
		for(String key : mapCl.keySet()){result.getCutList().add(mapCl.get(key));}
		
		if(!result.isSetCutList()){logger.warn("No CL found");}
		
		return result;
	}
	
	private CutListsAvailable find(String clKey)
	{	
		String sUrl = "http://cutlist.at/getxml.php?name="+clKey;
		
		EhResultContainer leh = new EhResultContainer();
		LogParser lp = new XmlParser(leh);
		
		LogListener ll = new LogListenerHttp(lp);
		ll.processMulti(sUrl);
		
		try
		{
			Document doc = ((JDomEvent)leh.getSingleResult()).getDoc();
			return getAvailableCutLists(doc);
		}
		catch (NoSuchElementException e) {return new CutListsAvailable();}
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
			catch (JDOMException e) {logger.error("",e);}
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