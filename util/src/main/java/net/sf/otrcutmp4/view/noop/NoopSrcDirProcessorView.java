package net.sf.otrcutmp4.view.noop;

import java.io.File;

import net.sf.otrcutmp4.interfaces.view.ViewSrcDirProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoopSrcDirProcessorView implements ViewSrcDirProcessor
{
	final static Logger logger = LoggerFactory.getLogger(NoopSrcDirProcessorView.class);

	@Override public void readFilesInDir(File srcDir) {}
	@Override public void found(int i) {}
	
}
