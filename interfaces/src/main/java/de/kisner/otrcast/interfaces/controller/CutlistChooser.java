package de.kisner.otrcast.interfaces.controller;

import java.util.List;

import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.series.Video;
import de.kisner.otrcast.model.xml.series.Videos;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;

public interface CutlistChooser
{
	Videos chooseCutlists(VideoFiles vFiles) throws UtilsProcessingException;
	List<Video> select(VideoFile vf);
	
//	void loadCurlists(VideoFiles vf);
//	void loadCutlists(Videos videos);
}