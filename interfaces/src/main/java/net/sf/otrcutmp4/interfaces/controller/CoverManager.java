package net.sf.otrcutmp4.interfaces.controller;

import java.io.IOException;

import net.sf.otrcutmp4.model.xml.series.Season;

public interface CoverManager
{
	public static enum TYPE {FS,FSW}
	public static enum Format {PNG,JPEG}
	
	boolean isAvailable(Season season);
	Format getFormat();
	byte[] getImageStream() throws IOException;
}