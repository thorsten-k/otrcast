package de.kisner.otrcast.controller.tag.writer;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.boxes.apple.AppleCoverBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleMediaTypeBox;
import com.coremedia.iso.boxes.apple.AppleShowBox;

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
	
	protected void writeCover(AppleItemListBox apple, String filename) throws IOException
	{
			AppleCoverBox box = null;
			if(apple.getBoxes(AppleCoverBox.class).isEmpty())
			{
				logger.debug(AppleShowBox.class.getSimpleName()+" is empty");
				box = new AppleCoverBox();
			}
			else
			{
				box = (AppleCoverBox) apple.getBoxes(AppleCoverBox.class).get(0);
				logger.debug(AppleCoverBox.class.getSimpleName()+" exists: "+box.getValue());
			}
			if (filename.endsWith("png"))
			{
				
				byte[] imageBytes = IOUtils.toByteArray(new FileInputStream(filename));
				box.setPng(imageBytes);
			}
			if (filename.endsWith("jpg"))
			{
				
				byte[] imageBytes = IOUtils.toByteArray(new FileInputStream(filename));
				box.setJpg(imageBytes);
			}
			apple.addBox(box);
	}
}