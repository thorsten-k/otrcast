package de.kisner.otrcast.factory.xml.mc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Storage;
import de.kisner.otrcast.model.xml.mc.File;
import de.kisner.otrcast.model.xml.otr.Query;
import net.sf.exlp.util.DateUtil;

public class XmlFileFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlFileFactory.class);
	
	private de.kisner.otrcast.model.xml.mc.File q;
	
	public XmlFileFactory(Query query){this(query.getFile());}
	public XmlFileFactory(de.kisner.otrcast.model.xml.mc.File q){this.q=q;}
	
	public File build(Storage ejb)
	{
		File xml = new File();
		
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		if(q.isSetHash()){xml.setHash(ejb.getHash());}
		if(q.isSetSize()){xml.setSize(ejb.getSize());}
		if(q.isSetLastModified()){xml.setLastModified(DateUtil.toXmlGc(ejb.getRecord()));}
		return xml;
	}
	
	public static File buildPathName(java.io.File file)
	{
		de.kisner.otrcast.model.xml.mc.File xml = new de.kisner.otrcast.model.xml.mc.File();
		xml.setPath(file.getParentFile().getAbsolutePath());
		xml.setName(file.getName());
		return xml;
	}
}