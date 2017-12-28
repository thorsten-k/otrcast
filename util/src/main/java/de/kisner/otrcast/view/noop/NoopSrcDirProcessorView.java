package de.kisner.otrcast.view.noop;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.view.client.ViewSrcDirProcessor;
import de.kisner.otrcast.model.xml.cut.VideoFiles;

public class NoopSrcDirProcessorView implements ViewSrcDirProcessor
{
	final static Logger logger = LoggerFactory.getLogger(NoopSrcDirProcessorView.class);

	@Override public void readFilesInDir(File srcDir) {}
	@Override public void found(int i) {}
	@Override public void found(VideoFiles files) {}
	
}
