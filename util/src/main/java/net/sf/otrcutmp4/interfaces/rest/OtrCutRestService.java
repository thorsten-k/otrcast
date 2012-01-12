package net.sf.otrcutmp4.interfaces.rest;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

public interface OtrCutRestService
{
	String addCutPackage(VideoFiles vFiles) throws UtilsProcessingException;
	VideoFiles findCutPackage(String token) throws UtilsProcessingException;
}
