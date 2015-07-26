package net.sf.otrcutmp4.controller.tag.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.otrcutmp4.interfaces.controller.CoverManager;

public abstract class AbstractTagWriter
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTagWriter.class);

	protected CoverManager coverManager;
	
	public AbstractTagWriter(CoverManager coverManager)
	{
		this.coverManager=coverManager;
	}
}