package net.sf.otrcutmp4.interfaces.web;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.series.Movies;

public interface WebMovieFinder
{
	Movies find(VideoFile videoFile) throws UtilsProcessingException;
	Movies find(String title) throws UtilsProcessingException;
}
