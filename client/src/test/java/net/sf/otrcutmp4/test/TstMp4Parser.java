package net.sf.otrcutmp4.test;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.IsoBufferWrapper;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoFileConvenienceHelper;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;

public class TstMp4Parser
{
	final static Logger logger = LoggerFactory.getLogger(TstMp4Parser.class);
	
	public static void main(String[] args) throws IOException
	{
        IsoBufferWrapper isoBufferWrapper = new IsoBufferWrapper(new File("m.mp4"));
        IsoFile isoFile = new IsoFile(isoBufferWrapper);
        isoFile.parse();
        MovieBox moov = isoFile.getBoxes(MovieBox.class).get(0);
        for(Box box : moov.getBoxes())
        {
        	 System.out.println(box.getSize());
        }
        
        UserDataBox udta = moov.getBoxes(UserDataBox.class).get(0);
        System.out.println(udta.getBoxes());
        System.out.println(udta.getUserType());
        AppleItemListBox appleItemListBox =
            (AppleItemListBox) IsoFileConvenienceHelper.get(isoFile, "moov/udta/meta/ilst");
        for(Box box : appleItemListBox.getBoxes())
        {
        	 System.out.println(box.toString()+" "+box.getSize());
        }
    }
}
