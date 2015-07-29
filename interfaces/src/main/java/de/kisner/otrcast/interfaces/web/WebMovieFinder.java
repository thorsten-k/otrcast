package de.kisner.otrcast.interfaces.web;

import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.series.Movies;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;

public interface WebMovieFinder
{
	Movies find(VideoFile videoFile) throws UtilsProcessingException;
	Movies find(String title) throws UtilsProcessingException;
}
