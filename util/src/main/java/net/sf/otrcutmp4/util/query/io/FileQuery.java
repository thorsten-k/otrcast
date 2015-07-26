package net.sf.otrcutmp4.util.query.io;

import java.io.FileFilter;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

public class FileQuery
{
	public static FileFilter mp4FileFilter()
	{
		IOFileFilter ffDir = FileFilterUtils.directoryFileFilter();
		IOFileFilter ffFile = FileFilterUtils.fileFileFilter();
		IOFileFilter ffMp4  = FileFilterUtils.suffixFileFilter(".mp4");
		
		IOFileFilter ff = FileFilterUtils.and(ffFile,ffMp4);
		        
		return FileFilterUtils.or(ffDir, ff);
	}
}

