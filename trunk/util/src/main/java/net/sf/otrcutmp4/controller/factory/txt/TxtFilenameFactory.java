package net.sf.otrcutmp4.controller.factory.txt;

import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.model.xml.cut.CutList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtFilenameFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtFilenameFactory.class);
	
	public TxtFilenameFactory()
	{
		
	}
	
	public String create(CutList cl) throws OtrProcessingException
	{
		return "test";
	}
}
