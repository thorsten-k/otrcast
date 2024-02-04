package de.kisner.otrcast.controller.cutlist;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.exlp.util.jx.JaxbUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.processor.exlp.parser.CutlistParser;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.interfaces.controller.CutlistLoader;
import de.kisner.otrcast.interfaces.view.client.ViewCutlistLoader;
import de.kisner.otrcast.model.xml.cut.Author;
import de.kisner.otrcast.model.xml.cut.Comment;
import de.kisner.otrcast.model.xml.cut.CutList;
import de.kisner.otrcast.model.xml.cut.CutLists;
import de.kisner.otrcast.model.xml.cut.FileName;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.video.Video;
import de.kisner.otrcast.model.xml.video.Videos;
import net.sf.exlp.core.event.JDomEvent;
import net.sf.exlp.core.event.JaxbEvent;
import net.sf.exlp.core.handler.EhResultContainer;
import net.sf.exlp.core.listener.LogListenerHttp;
import net.sf.exlp.core.parser.XmlParser;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;

public class JdomCutlistLoader implements CutlistLoader
{
	final static Logger logger = LoggerFactory.getLogger(JdomCutlistLoader.class);
	
	private final ViewCutlistLoader view;
	
	public JdomCutlistLoader(ViewCutlistLoader view)
	{
		this.view=view;
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
		view.cutlistsFound(vFiles);
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
	
	@Override public void loadCuts(Videos videos)
	{
		for(Video video : videos.getVideo())
		{
			if(video.isSetVideoFiles())
			{
				for(VideoFile vf : video.getVideoFiles().getVideoFile())
				{
					if(vf.isSetCutList())
					{
						logger.info("Loading");
						JaxbUtil.info(vf);
						CutList cl = loadCutlist(vf.getCutList().getId());
						vf.setCutList(cl);
					}
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
		
		CutList cutlist = (CutList)event.getObject();
		cutlist.setId(id);
		return cutlist;
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