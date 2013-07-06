package net.sf.otrcutmp4.interfaces.controller;

import java.util.List;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.model.xml.series.Videos;

public interface CutlistChooser
{
	Videos chooseCutlists(VideoFiles vFiles) throws UtilsProcessingException;
	List<Video> select(VideoFile vf);
	
//	void loadCurlists(VideoFiles vf);
//	void loadCutlists(Videos videos);
}