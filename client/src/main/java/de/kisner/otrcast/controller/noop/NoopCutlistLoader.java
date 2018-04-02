package de.kisner.otrcast.controller.noop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.controller.CutlistLoader;
import de.kisner.otrcast.model.xml.video.Videos;

public class NoopCutlistLoader implements CutlistLoader
{
	final static Logger logger = LoggerFactory.getLogger(NoopCutlistLoader.class);

	@Override public void loadCuts(Videos videos) {} 	
}