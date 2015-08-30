package de.kisner.otrcast.controller.tag.writer;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.tag.util.Mp4BoxManager;

public class MetaTagWriter extends AbstractTagWriter
{
	final static Logger logger = LoggerFactory.getLogger(MetaTagWriter.class);

	public MetaTagWriter()
	{
		super(null);
	}

    public void writeMediaType(File srcFile, Mp4BoxManager.Type type, File dstFile) throws IOException
    {
    	readMp4Box(srcFile);

        writeMediaType(apple,type);
        
        writeMp4(dstFile);
    }
}