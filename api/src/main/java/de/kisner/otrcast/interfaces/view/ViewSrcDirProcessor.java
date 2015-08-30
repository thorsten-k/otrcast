package de.kisner.otrcast.interfaces.view;

import java.io.File;

public interface ViewSrcDirProcessor
{
	void readFilesInDir(File srcDir);
	void found(int i);
}
