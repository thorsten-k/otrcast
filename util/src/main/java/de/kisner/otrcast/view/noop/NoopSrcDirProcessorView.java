package de.kisner.otrcast.view.noop;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.view.ViewSrcDirProcessor;

public class NoopSrcDirProcessorView implements ViewSrcDirProcessor
{
	final static Logger logger = LoggerFactory.getLogger(NoopSrcDirProcessorView.class);

	@Override public void readFilesInDir(File srcDir) {}
	@Override public void found(int i) {}
	
}
