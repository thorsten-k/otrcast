package net.sf.otrcutmp4.controller.tag;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.otrcutmp4.controller.tag.reader.Mp4TagReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Mp4MediaTypeCorrector
{
	final static Logger logger = LoggerFactory.getLogger(Mp4MediaTypeCorrector.class);

    private Mp4TagReader tagReader;

	public Mp4MediaTypeCorrector()
	{
        tagReader = new Mp4TagReader(false);
	}

    public void correct(File file) throws IOException
    {
        try
        {
            Mp4BoxManager.Type type = tagReader.readMediaType(file);
        }
        catch (UtilsNotFoundException e)
        {
            Mp4BoxManager.Type type = tagReader.guessType(file);
            logger.info("MediaType not set. Guessing to "+type);

//            File dst = new File(file.getParent(),"x.mp4");

//            Mp4MediaTypeWriter mtw = new Mp4MediaTypeWriter();
//            mtw.writeMediaType(file,type,dst);
        }
    }
}