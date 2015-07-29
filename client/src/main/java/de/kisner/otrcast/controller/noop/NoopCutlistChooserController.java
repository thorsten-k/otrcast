package de.kisner.otrcast.controller.noop;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.controller.CutlistChooser;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.series.Video;
import de.kisner.otrcast.model.xml.series.Videos;

public class NoopCutlistChooserController implements CutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(NoopCutlistChooserController.class); 
	
	@Override public Videos chooseCutlists(VideoFiles vFiles) {return null;}
	@Override public List<Video> select(VideoFile vf) {return null;}
}