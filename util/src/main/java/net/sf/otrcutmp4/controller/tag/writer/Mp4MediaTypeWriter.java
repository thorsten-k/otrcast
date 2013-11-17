package net.sf.otrcutmp4.controller.tag.writer;

import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleMediaTypeBox;
import net.sf.otrcutmp4.controller.tag.Mp4BoxManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mp4MediaTypeWriter
{
	final static Logger logger = LoggerFactory.getLogger(Mp4MediaTypeWriter.class);

	public Mp4MediaTypeWriter()
	{

	}
	
	public void writeMediaType(AppleItemListBox apple, Mp4BoxManager.Type type)
	{
        AppleMediaTypeBox box = null;
        if(apple.getBoxes(AppleMediaTypeBox.class).isEmpty())
        {
            box = new AppleMediaTypeBox();
        }
        else
        {
            box = apple.getBoxes(AppleMediaTypeBox.class).get(0);
        }
        switch(type)
        {
            case SERIES: box.setValue(Mp4BoxManager.typeSeries);break;
            default:    logger.warn("Type writing of "+type+" NYI");
        }

        apple.addBox(box);
	}
}