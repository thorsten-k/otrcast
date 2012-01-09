package net.sf.otrcutmp4.interfaces.rest;

import net.sf.otrcutmp4.controller.exception.OtrNotFoundException;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

public interface OtrCutRestService
{
	public String request(VideoFiles cutRequest);
	public VideoFiles processed(String token) throws OtrNotFoundException;
}
