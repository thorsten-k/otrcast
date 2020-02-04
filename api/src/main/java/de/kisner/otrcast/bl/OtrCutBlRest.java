package de.kisner.otrcast.bl;

import javax.ws.rs.PathParam;

import org.jeesl.exception.processing.UtilsProcessingException;

import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.video.Videos;


public interface OtrCutBlRest
{
	String addCutPackage(VideoFiles vFiles) throws UtilsProcessingException;
	Videos findCutPackage(@PathParam("token") String token) throws UtilsProcessingException;
}