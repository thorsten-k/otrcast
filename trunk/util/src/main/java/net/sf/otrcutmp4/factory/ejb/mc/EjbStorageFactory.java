package net.sf.otrcutmp4.factory.ejb.mc;

import java.io.File;
import java.util.Date;

import net.sf.otrcutmp4.interfaces.model.Storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbStorageFactory<STORAGE extends Storage>
{	
final static Logger logger = LoggerFactory.getLogger(EjbStorageFactory.class);
	
	final Class<STORAGE> clStorage;
	
	 public EjbStorageFactory(final Class<STORAGE> clStorage)
	 {
	        this.clStorage=clStorage;
	 }
	 
	 public static <STORAGE extends Storage> EjbStorageFactory<STORAGE> factory(final Class<STORAGE> clStorage)
	 {
		 return new EjbStorageFactory<STORAGE>(clStorage);
	 }
	
	public STORAGE build(net.sf.otrcutmp4.model.xml.mc.Storage xml)
	{
		return build(xml.getName(),xml.getHash(),xml.getSize(),xml.getLastModified().toGregorianCalendar().getTime());
	}
	
	public STORAGE build(File f)
	{
		return build(f.getAbsolutePath(),null,f.length(),new Date(f.lastModified()));
	}
	
	public STORAGE build(String name, String hash, long size, Date lastModifed)
	{
		STORAGE ejb = null;
		
		try{ejb = clStorage.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		ejb.setName(name);
		ejb.setHash(hash);
		ejb.setSize(size);
	
		
		return ejb;
	}
}