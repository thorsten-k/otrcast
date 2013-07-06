package net.sf.otrcutmp4.factory.ejb.mc;

import net.sf.otrcutmp4.interfaces.model.Cover;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbCoverFactory<SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER>,COVER extends Cover>
{	
	final static Logger logger = LoggerFactory.getLogger(EjbCoverFactory.class);
	
	final Class<COVER> coverClass;
	
	 public EjbCoverFactory(final Class<COVER> coverClass)
	 {
	        this.coverClass=coverClass;
	 }
	 
	 public static <SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER>,COVER extends Cover>
	 	EjbCoverFactory<SERIES,SEASON,EPISODE,COVER> factory(final Class<COVER> coverClass)
	 {
		 return new EjbCoverFactory<SERIES,SEASON,EPISODE,COVER>(coverClass);
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