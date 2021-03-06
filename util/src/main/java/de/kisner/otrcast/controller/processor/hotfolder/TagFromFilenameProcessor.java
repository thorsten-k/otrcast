package de.kisner.otrcast.controller.processor.hotfolder;

import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.tag.writer.SeriesTagWriter;
import de.kisner.otrcast.model.xml.video.tv.Episode;
import de.kisner.otrcast.model.xml.video.tv.Season;
import de.kisner.otrcast.model.xml.video.tv.Series;

public class TagFromFilenameProcessor implements Processor
{

		final static Logger logger = LoggerFactory.getLogger(TagFromFilenameProcessor.class);
	
		private SeriesTagWriter tagger;
		
		@SuppressWarnings("rawtypes")
		@Override
		public void process(Exchange exchange) throws Exception {
			GenericFile file = (GenericFile) exchange.getIn().getBody();
			File jFile       = (File)file.getFile();
			
			
			Episode episode = createEpisodeFromFilename(file.getFileName());
			tagger = new SeriesTagWriter();
			tagger.tagEpisode(jFile, episode, new File("target/test.mp4"));		
		}
		
		
		//In case the filename is constructed as Series-S01-E01-Episodetitle, the meta data can be taken from it
		public Episode createEpisodeFromFilename(String filename)
		{
			Integer break1  = filename.indexOf("-");
			Integer break2  = break1+4;
			Integer break3  = break2+4;

			String series   = filename.substring(0, break1);
			String season   = filename.substring(break1+2, break2);
			String episode  = filename.substring(break2+2, break3);
			String title    = filename.substring(break3+1, filename.length()-4);
			
			Series seriesObj = new Series();
			seriesObj.setName(splitCamelCase(series));
			
			Season seasonObj   = new Season();
			seasonObj.setNr(new Integer(season));
			seasonObj.setSeries(seriesObj);
			
			Episode episodeObj = new Episode();
			episodeObj.setSeason(seasonObj);
			episodeObj.setName(splitCamelCase(title));
			episodeObj.setNr(new Integer(episode));
			return episodeObj;
		}
		
		static String splitCamelCase(String s) {
			   return s.replaceAll(
			      String.format("%s|%s|%s",
			         "(?<=[A-Z])(?=[A-Z][a-z])",
			         "(?<=[^A-Z])(?=[A-Z])",
			         "(?<=[A-Za-z])(?=[^A-Za-z])"
			      ),
			      " "
			   );
			}
		
	}