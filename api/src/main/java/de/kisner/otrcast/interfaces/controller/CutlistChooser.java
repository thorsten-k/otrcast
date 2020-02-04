package de.kisner.otrcast.interfaces.controller;

import java.util.List;

import org.jeesl.exception.processing.UtilsProcessingException;

import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.video.Video;
import de.kisner.otrcast.model.xml.video.Videos;

public interface CutlistChooser
{
	Videos chooseCutlists(VideoFiles vFiles) throws UtilsProcessingException;
	List<Video> select(VideoFile vf);
	
//	void loadCurlists(VideoFiles vf);
//	void loadCutlists(Videos videos);
}