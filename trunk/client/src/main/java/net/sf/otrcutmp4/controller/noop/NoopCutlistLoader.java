package net.sf.otrcutmp4.controller.noop;

import net.sf.otrcutmp4.interfaces.controller.CutlistLoader;
import net.sf.otrcutmp4.model.xml.series.Videos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoopCutlistLoader implements CutlistLoader
{
	final static Logger logger = LoggerFactory.getLogger(NoopCutlistLoader.class);

	@Override public void loadCuts(Videos videos) {} 	
}