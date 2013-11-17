package net.sf.otrcutmp4.controller.tag;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.*;
import com.googlecode.mp4parser.util.Path;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * taken from https://mp4parser.googlecode.com/svn/trunk/examples/src/main/java/com/googlecode/mp4parser/stuff/ChangeMetaData.java
 */
public class Mp4MetadataBalancer
{
	final static Logger logger = LoggerFactory.getLogger(Mp4MetadataBalancer.class);

    private long sizeBefore,sizeAfter;

	public Mp4MetadataBalancer()
	{
		
	}

    public void saveInitialState(MovieBox moov)
    {
        sizeBefore = moov.getSize();
    }
    public void saveFinalState(MovieBox moov)
    {
        sizeAfter = moov.getSize();
    }
	
    public boolean needsOffsetCorrection(IsoFile isoFile)
    {
        if (Path.getPaths(isoFile, "mdat").size() > 1)
        {
            throw new RuntimeException("There might be the weird case that a file has two mdats. One before" +
                    " moov and one after moov. That would need special handling therefore I just throw an " +
                    "exception here. ");
        }

        if (Path.getPaths(isoFile, "moof").size() > 0)
        {
            throw new RuntimeException("Fragmented MP4 files need correction, too. (But I would need to look where)");
        }

        for (Box box : isoFile.getBoxes())
        {
            if ("mdat".equals(box.getType()))
            {
            	logger.debug(IsoFile.class.getSimpleName()+" needs no correction");
                return false;
            }
            if ("moov".equals(box.getType()))
            {
            	logger.debug(IsoFile.class.getSimpleName()+" needs correction");
                return true;
            }
        }
        throw new RuntimeException("Hmmm - shouldn't happen");
    }

    public void correctChunkOffsets(IsoFile isoFile)
    {
        if(needsOffsetCorrection(isoFile))
        {
            long correction = sizeAfter - sizeBefore;
            correctChunkOffsets(isoFile, correction);
        }
    }

    public void correctChunkOffsets(IsoFile tempIsoFile, long correction)
    {
        long correctionInternal = sizeAfter - sizeBefore;
    	logger.debug("Correcting ChunkOffset: "+correction+" (internal computation ="+correctionInternal+")");
        List<Box> chunkOffsetBoxes = Path.getPaths(tempIsoFile, "/moov[0]/trak/mdia[0]/minf[0]/stbl[0]/stco[0]");
        for (Box chunkOffsetBox : chunkOffsetBoxes)
        {
            LinkedList<Box> stblChildren = new LinkedList<Box>(chunkOffsetBox.getParent().getBoxes());
            stblChildren.remove(chunkOffsetBox);

            long[] cOffsets = ((ChunkOffsetBox) chunkOffsetBox).getChunkOffsets();
            for (int i = 0; i < cOffsets.length; i++)
            {
                cOffsets[i] += correction;
            }

            StaticChunkOffsetBox cob = new StaticChunkOffsetBox();
            cob.setChunkOffsets(cOffsets);
            stblChildren.add(cob);
            chunkOffsetBox.getParent().setBoxes(stblChildren);
        }
    }
    
    protected void writeRandomMetadata(File srcFile, String videoFilePath, String text, File dstFile) throws IOException
    {
        File tempFile = null;
        FileOutputStream videoFileOutputStream = null;
        IsoFile tempIsoFile = null;

        try
        {
            File videoFile = new File(videoFilePath);
            FileUtils.copyFile(srcFile, videoFile);
            if (!videoFile.exists()){throw new FileNotFoundException("File " + videoFilePath + " not exists");}
            if (!videoFile.canWrite()){throw new IllegalStateException("No write permissions to file " + videoFilePath);}
            
            tempFile = File.createTempFile("ChangeMetaData", "");
            FileUtils.copyFile(videoFile, tempFile);

            tempIsoFile = new IsoFile(tempFile.getAbsolutePath());

            UserDataBox userDataBox;
            long sizeBefore;
            if ((userDataBox = (UserDataBox) Path.getPath(tempIsoFile, "/moov/udta")) == null)
            {
                sizeBefore = 0;
                userDataBox = new UserDataBox();
            }
            else
            {
                sizeBefore = userDataBox.getSize();
            }
            
            MetaBox metaBox;
            if ((metaBox = (MetaBox) Path.getPath(userDataBox, "meta")) == null)
            {
                metaBox = new MetaBox();
                userDataBox.addBox(metaBox);
            }


            XmlBox xmlBox = new XmlBox();
            xmlBox.setXml(text);
             metaBox.addBox(xmlBox);

            long sizeAfter = userDataBox.getSize();
            if (needsOffsetCorrection(tempIsoFile))
            {
                correctChunkOffsets(tempIsoFile, sizeAfter - sizeBefore);
            }
            videoFileOutputStream = new FileOutputStream(videoFile);
            tempIsoFile.getBox(videoFileOutputStream.getChannel());
        }
        finally
        {
            IOUtils.closeQuietly(tempIsoFile);
            IOUtils.closeQuietly(videoFileOutputStream);
            FileUtils.deleteQuietly(tempFile);
        }
    }
}