package net.sf.otrcutmp4.controller.tagger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MovieBox;

public class Mp4BoxManager
{
	final static Logger logger = LoggerFactory.getLogger(Mp4BoxManager.class);

	public static MovieBox movieBox(IsoFile isoFile)
	{
		return isoFile.getBoxes(MovieBox.class).get(0);
	}
}