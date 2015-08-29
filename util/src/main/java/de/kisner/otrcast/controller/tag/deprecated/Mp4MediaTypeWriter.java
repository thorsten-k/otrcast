package de.kisner.otrcast.controller.tag.deprecated;

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
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleMediaTypeBox;

public class Mp4MediaTypeWriter
{
	final static Logger logger = LoggerFactory.getLogger(Mp4MediaTypeWriter.class);

	public Mp4MediaTypeWriter()
	{

	}

    public void writeMediaType(File srcFile, Mp4BoxManager.Type type, File dstFile) throws IOException
    {
        Mp4MetadataBalancer mdb = new Mp4MetadataBalancer();

        RandomAccessFile rafR = new RandomAccessFile(srcFile, "r");
        RandomAccessFile rafW = new RandomAccessFile(dstFile, "rw");

        FileChannel fcr = rafR.getChannel();
        FileChannel fcw = rafW.getChannel();

        IsoFile isoFile = new IsoFile(fcr);

        MovieBox moov = Mp4BoxManager.movieBox(isoFile);
        UserDataBox udta = Mp4BoxManager.userDataBox(moov);
        MetaBox meta = Mp4BoxManager.metaBox(udta);
        AppleItemListBox apple = Mp4BoxManager.appleItemListBox(meta);

        mdb.saveInitialState(moov);
        writeMediaType(apple,Mp4BoxManager.Type.SERIES);
        mdb.saveFinalState(moov);

        mdb.correctChunkOffsets(isoFile);

        isoFile.getBox(fcw);
        fcw.force(true);fcw.close();rafW.close();
        fcr.close();rafR.close();
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