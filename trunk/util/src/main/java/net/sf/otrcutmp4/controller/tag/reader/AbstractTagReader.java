package net.sf.otrcutmp4.controller.tag.reader;

import net.sf.otrcutmp4.model.xml.mc.Cover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.boxes.apple.AppleCoverBox;
import com.coremedia.iso.boxes.apple.AppleDataBox;

public class AbstractTagReader
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTagReader.class);	

	public AbstractTagReader()
	{

	}
	
	protected Cover getCover(AppleCoverBox box) throws NoSuchFieldException
	{
		if(box.getBoxes(AppleDataBox.class).size()==0){throw new NoSuchFieldException();}	
		AppleDataBox adb = box.getBoxes(AppleDataBox.class).get(0);
		
		Cover cover = new Cover();
		cover.setData(adb.getData());
		
		//see source of AppleCoverBox.java
		if(adb.getFlags()==0xe){cover.setType("png");}
		else if(adb.getFlags()==0xd){cover.setType("jpg");}
		else {logger.warn("Unknown flag for cover "+adb.getFlags());}
		
		return cover;
	}
}