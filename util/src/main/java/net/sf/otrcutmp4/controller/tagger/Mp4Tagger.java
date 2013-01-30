package net.sf.otrcutmp4.controller.tagger;

import java.io.File;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.series.Episode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mp4Tagger
{
	final static Logger logger = LoggerFactory.getLogger(Mp4Tagger.class);
		
	public Mp4Tagger()
	{

	}
	
	public void tagEpisode(File f, Episode episode)
	{
		JaxbUtil.info(episode);
	}
}