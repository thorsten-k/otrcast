package net.sf.otrcutmp4.controller.tag.reader;

import net.sf.otrcutmp4.model.xml.mc.Image;

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
	
	protected Image getCover(AppleCoverBox box) throws NoSuchFieldException
	{
		if(box.getBoxes(AppleDataBox.class).size()==0){throw new NoSuchFieldException();}	
		AppleDataBox adb = box.getBoxes(AppleDataBox.class).get(0);
		
		Image cover = new Image();
		cover.setData(adb.getData());
		
		//see source of AppleCoverBox.java
		if(adb.getFlags()==0xe){cover.setFileType("png");}
		else if(adb.getFlags()==0xd){cover.setFileType("jpg");}
		else {logger.warn("Unknown flag for cover "+adb.getFlags());}
		
		return cover;
	}
}