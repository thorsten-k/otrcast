package de.kisner.otrcast.interfaces.web;

import org.jeesl.exception.processing.UtilsProcessingException;

import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.video.tv.Movies;

public interface WebMovieFinder
{
	Movies find(VideoFile videoFile) throws UtilsProcessingException;
	Movies find(String title) throws UtilsProcessingException;
}
