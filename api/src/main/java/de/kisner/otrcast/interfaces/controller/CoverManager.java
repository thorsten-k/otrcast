package de.kisner.otrcast.interfaces.controller;

import java.io.IOException;

import de.kisner.otrcast.model.xml.video.tv.Season;

public interface CoverManager
{
	public static enum TYPE {FS,FSW}
	public static enum Format {PNG,JPEG}
	
	boolean isAvailable(Season season);
	
	Format getFormat();
	byte[] getImageStream() throws IOException;
}