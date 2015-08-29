package de.kisner.otrcast.controller.tag.writer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.apple.AppleCoverBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleMediaTypeBox;

import de.kisner.otrcast.controller.tag.util.Mp4BoxManager;
import de.kisner.otrcast.controller.tag.util.Mp4MetadataBalancer;
import de.kisner.otrcast.interfaces.controller.CoverManager;

public abstract class AbstractTagWriter
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTagWriter.class);

	protected CoverManager coverManager;
	
	protected AppleItemListBox apple;
	private MovieBox moov;
	private IsoFile isoFile;
	
	private RandomAccessFile rafR;
	private FileChannel fcr;
	
	private Mp4MetadataBalancer mdb;
	
	public AbstractTagWriter(CoverManager coverManager)
	{
		this.coverManager=coverManager;
	}
	
	protected void readMp4Box(File srcFile) throws IOException
	{
		rafR = new RandomAccessFile(srcFile, "r");
		fcr = rafR.getChannel();
		
		isoFile = new IsoFile(fcr);
		
		moov = Mp4BoxManager.movieBox(isoFile);
		mdb = new Mp4MetadataBalancer();
		mdb.saveInitialState(moov);
		
		UserDataBox udta = Mp4BoxManager.userDataBox(moov);
		MetaBox meta = Mp4BoxManager.metaBox(udta);
		apple = Mp4BoxManager.appleItemListBox(meta);
	}
	
	protected void writeMp4(File dstFile) throws IOException
	{
		mdb.saveFinalState(moov);
		mdb.correctOffsetsIfRequired(isoFile);
		
		RandomAccessFile rafW = new RandomAccessFile(dstFile, "rw");
		FileChannel fcw = rafW.getChannel();
		
		isoFile.getBox(fcw);
		fcw.force(true);fcw.close();rafW.close();
		fcr.close();
		rafR.close();
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
	
	protected void writeCover(AppleItemListBox apple, CoverManager.Format format, byte[] imageBytes) throws IOException
	{
		AppleCoverBox box = Mp4BoxManager.fcAppleCoverBox(apple);
		switch(format)
		{
			case PNG:  box.setPng(imageBytes);break;
			case JPEG: box.setJpg(imageBytes);break;
		}
		apple.addBox(box);
	}
}