package de.kisner.otrcast.bl;

import javax.ws.rs.PathParam;

import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.series.Videos;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;


public interface OtrCutBlRest
{
	String addCutPackage(VideoFiles vFiles) throws UtilsProcessingException;
	Videos findCutPackage(@PathParam("token") String token) throws UtilsProcessingException;
}