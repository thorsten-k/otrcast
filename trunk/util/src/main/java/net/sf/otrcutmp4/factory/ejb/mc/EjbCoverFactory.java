package net.sf.otrcutmp4.factory.ejb.mc;

import net.sf.otrcutmp4.interfaces.model.Image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbCoverFactory<COVER extends Image>
{	
final static Logger logger = LoggerFactory.getLogger(EjbCoverFactory.class);
	
	final Class<COVER> coverClass;
	
	 public EjbCoverFactory(final Class<COVER> coverClass)
	 {
	        this.coverClass=coverClass;
	 }
	 
	 public static <COVER extends Image> EjbCoverFactory<COVER> factory(final Class<COVER> coverClass)
	 {
		 return new EjbCoverFactory<COVER>(coverClass);
	 }
	
	public COVER build(net.sf.otrcutmp4.model.xml.mc.Cover cover)
	{
		COVER ejb = null;
		
		try{ejb = coverClass.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		ejb.setType(cover.getType());
		ejb.setData(cover.getData());
		
		return ejb;
	}
}