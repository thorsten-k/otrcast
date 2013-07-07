package net.sf.otrcutmp4.factory.ejb.mc;

import net.sf.otrcutmp4.interfaces.model.Storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbStorageFactory<STORAGE extends Storage>
{	
final static Logger logger = LoggerFactory.getLogger(EjbStorageFactory.class);
	
	final Class<STORAGE> coverClass;
	
	 public EjbStorageFactory(final Class<STORAGE> coverClass)
	 {
	        this.coverClass=coverClass;
	 }
	 
	 public static <STORAGE extends Storage> EjbStorageFactory<STORAGE> factory(final Class<STORAGE> coverClass)
	 {
		 return new EjbStorageFactory<STORAGE>(coverClass);
	 }
	
	public STORAGE build(net.sf.otrcutmp4.model.xml.mc.Storage xml)
	{
		return build(xml.getName(),xml.getHash(),xml.getSize());
	}
	
	public STORAGE build(String name, String hash, long size)
	{
		STORAGE ejb = null;
		
		try{ejb = coverClass.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		ejb.setName(name);
		ejb.setHash(hash);
		ejb.setSize(size);
		
		return ejb;
	}
}