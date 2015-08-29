package de.kisner.otrcast.controller.tag.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleMediaTypeBox;

import de.kisner.otrcast.interfaces.controller.CoverManager;

public abstract class AbstractTagWriter
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTagWriter.class);

	protected CoverManager coverManager;
	
	public AbstractTagWriter(CoverManager coverManager)
	{
		this.coverManager=coverManager;
	}
	
	protected void writeMediaType(AppleItemListBox apple, String type)
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
		box.setValue(type);
		apple.addBox(box);
	}
}