package net.sf.otrcutmp4.controller.cutlist;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import net.sf.exlp.core.event.JDomEvent;
import net.sf.exlp.core.event.JaxbEvent;
import net.sf.exlp.core.handler.EhResultContainer;
import net.sf.exlp.core.listener.LogListenerHttp;
import net.sf.exlp.core.parser.XmlParser;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.processor.exlp.parser.CutlistParser;
import net.sf.otrcutmp4.factory.xml.otr.XmlOtrIdFactory;
import net.sf.otrcutmp4.interfaces.controller.CutlistLoader;
import net.sf.otrcutmp4.model.xml.cut.Author;
import net.sf.otrcutmp4.model.xml.cut.Comment;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.CutLists;
import net.sf.otrcutmp4.model.xml.cut.FileName;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.model.xml.series.Videos;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCutlistLoader implements CutlistLoader
{
	final static Logger logger = LoggerFactory.getLogger(DefaultCutlistLoader.class);
		
	public DefaultCutlistLoader()
	{

	}
	
	@Override
	public void loadCuts(Videos videos)
	{
		for(Video video : videos.getVideo())
		{
			if(video.isSetVideoFiles())
			{
				for(VideoFile vf : video.getVideoFiles().getVideoFile())
				{
					CutList cl = loadCutlist(vf.getCutList().getId());
					vf.setCutList(cl);
				}
			}
		}
	}
	
	private CutList loadCutlist(String id)
	{
		String http = "http://cutlist.at/getfile.php?id="+id;
		
		logger.info("Trying to download cutlist "+id);
		logger.debug("\t"+http);
	
		EhResultContainer leh = new EhResultContainer();
		LogParser lp = new CutlistParser(leh);
		
		LogListener ll = new LogListenerHttp(lp);
		ll.processSingle(http);
		
		JaxbEvent event = (JaxbEvent)leh.getSingleResult();
		return (CutList)event.getObject();
	}
	
	public VideoFiles searchCutlist(VideoFiles vFiles)
	{
		JaxbUtil.trace(vFiles);
		logger.info(" ");
		for(VideoFile vf : vFiles.getVideoFile())
		{
			CutLists avlCutLists = searchCutlist(vf);
			if(avlCutLists.getCutList().size()>0){vf.setCutLists(avlCutLists);}
		}
		return vFiles;
	}
	
	public CutLists searchCutlist(VideoFile vf)
	{
		CutLists result = new CutLists();
		
		logger.info("Searching for "+vf.getOtrId().getKey()+"."+vf.getOtrId().getFormat().getType());
		StringBuffer sb = new StringBuffer();
		sb.append(vf.getOtrId().getKey()).append(".").append(vf.getOtrId().getFormat().getType());
		
		Map<String,CutList> mapCl = new Hashtable<String,CutList>();
		
		for(CutList cl : find(vf.getOtrId().getKey()+"."+vf.getOtrId().getFormat().getType()).getCutList())
		{
			mapCl.put(cl.getId(), cl);
		}
		
		if(vf.getOtrId().getFormat().getType().equals(XmlOtrIdFactory.typeAviHq))
		{
			for(CutList cl : find(vf.getOtrId().getKey()+".mpg.HQ").getCutList())
			{
				mapCl.put(cl.getId(), cl);
			}
		}
		else
		{
			logger.warn("Format NYI: "+vf.getOtrId().getFormat().getType());
		}
		
		for(String key : mapCl.keySet()){result.getCutList().add(mapCl.get(key));}
		
		if(!result.isSetCutList()){logger.warn("No CL found");}
		
		return result;
	}
	
	private CutLists find(String clKey)
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
		catch (NoSuchElementException e) {return new CutLists();}
	}
	
	private CutLists getAvailableCutLists(Document doc)
	{
		CutLists cls = new CutLists();
		if(doc!=null)
		{
			XPathExpression<Element> xpath = XPathFactory.instance().compile("/files/cutlist", Filters.element());
			List<Element> elements = xpath.evaluate(doc);
			for (Element e : elements)
			{
				cls.getCutList().add(getCutList(e));
			}
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