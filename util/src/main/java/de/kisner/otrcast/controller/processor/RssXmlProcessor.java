package de.kisner.otrcast.controller.processor;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.rss.Rss;
import net.sf.exlp.util.xml.JaxbUtil;

public class RssXmlProcessor
{	
	final static Logger logger = LoggerFactory.getLogger(RssXmlProcessor.class);
	
	private Namespace nsRss;
	private Namespace nsContent;
	private Namespace nsWfw;
	private Namespace nsItunes;
	private Namespace nsDc;
	
	public RssXmlProcessor()
	{
		nsRss = Namespace.getNamespace("http://otrcutmp4.sf.net/rss");
		nsItunes = Namespace.getNamespace("itunes","http://www.itunes.com/dtds/podcast-1.0.dtd");
		nsContent = Namespace.getNamespace("content","http://purl.org/rss/1.0/modules/content/");
		nsWfw = Namespace.getNamespace("wfw","http://wellformedweb.org/CommentAPI/");
		nsDc = Namespace.getNamespace("dc","http://purl.org/dc/elements/1.1/");
	}
	
	public Document transform(Rss rss)
	{
		Namespace ns = Namespace.getNamespace("");
		
		Document doc = JaxbUtil.toDocument(rss);
		unsetNameSpace(doc.getRootElement(),ns);
		doc.getRootElement().removeNamespaceDeclaration(nsRss);
		doc.getRootElement().addNamespaceDeclaration(nsContent);
		doc.getRootElement().addNamespaceDeclaration(nsDc);
		doc.getRootElement().addNamespaceDeclaration(nsWfw);
//		doc.getRootElement().addNamespaceDeclaration(nsItunes);
		return doc;
	}
	
	private Element unsetNameSpace(Element e, Namespace nsEmpty)
	{
		if(e.getNamespace().equals(nsRss))
		{
			e.setNamespace(nsEmpty);
		}
		for(Element child : e.getChildren())
		{
			child=unsetNameSpace(child,nsEmpty);
		}
		return e;
	} 
}