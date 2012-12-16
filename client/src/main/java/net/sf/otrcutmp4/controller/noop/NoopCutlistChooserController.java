package net.sf.otrcutmp4.controller.noop;

import java.util.List;

import net.sf.otrcutmp4.interfaces.controller.CutlistChooser;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.model.xml.series.Videos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoopCutlistChooserController implements CutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(NoopCutlistChooserController.class); 
	
	@Override public Videos chooseCutlists(VideoFiles vFiles) {return null;}
	@Override public List<Video> select(VideoFile vf) {return null;}
}