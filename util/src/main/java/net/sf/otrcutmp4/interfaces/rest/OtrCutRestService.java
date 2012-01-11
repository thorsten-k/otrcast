package net.sf.otrcutmp4.interfaces.rest;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

public interface OtrCutRestService
{
	String addSelection(VideoFiles cutRequest);
	VideoFiles getSelection(String token) throws UtilsNotFoundException;
}
