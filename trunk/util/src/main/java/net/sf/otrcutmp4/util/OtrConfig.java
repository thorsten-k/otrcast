package net.sf.otrcutmp4.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.otrcutmp4.controller.exception.OtrConfigurationException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtrConfig
{
	final static Logger logger = LoggerFactory.getLogger(OtrConfig.class);
	
	public static enum Dir{HQAVI,TMP,BAT,HDAVI,RENAME,TOOLS,AC3,MP4,AVI};
	public static enum Tool{LAME,MP4BOX,FFMPEG,FAAC};
	public static enum Audio{FAAC};
	public static enum Url{GAE};
	
	public static String otrConfigName = "properties.txt";
		
	public static final String dirHqAvi = "dir.hq.avi";
	public static final String dirHdAc3 = "dir.hd.ac3";
	public static final String dirHdAvi = "dir.hd.avi";
	public static final String dirAvi = "dir.avi";
	public static final String dirMp4 = "dir.mp4";
	public static final String dirTmp = "dir.tmp";
	public static final String dirBat = "dir.bat";
	public static final String dirTools = "dir.tools";
	public static final String dirCutlists = "dir.cutlists";
	public static final String dirRename = "dir.rename";
	
	public static final String toolMp4Box = "tool.mp4box";
	public static final String toolLame = "tool.lame";
	public static final String toolFfmpeg = "tool.ffmpeg";
	public static final String toolFaac = "tool.faac";
	
	public static final String paraAudioFaac = "audio.faac.kbit";
	
	public static final String urlOtrSeries = "url.otrseries";
	
	private Map<Dir,String> mapDir;
	private Map<Tool,String> mapTool;
	private Map<Audio,String> mapAudio;
	private Map<Url,String> mapUrl;
	
	private List<String> lDirectotries,lTools;
	private Configuration config;
	
	public OtrConfig(){this(null);}
	public OtrConfig(Configuration config)
	{
		this.config=config;
		initDirectoryList();
		initToolList();
		initParameterList();
		initUrlList();
	}
	
	private void initDirectoryList()
	{
		mapDir = new Hashtable<Dir,String>();
		lDirectotries = new ArrayList<String>();
		lDirectotries.add(dirHqAvi);mapDir.put(Dir.HQAVI, dirHqAvi);
		lDirectotries.add(dirHdAvi);mapDir.put(Dir.HDAVI, dirHdAvi);
		lDirectotries.add(dirHdAc3);mapDir.put(Dir.AC3, dirHdAc3);
		lDirectotries.add(dirAvi);mapDir.put(Dir.AVI, dirAvi);
		lDirectotries.add(dirMp4);mapDir.put(Dir.MP4, dirMp4);
		lDirectotries.add(dirTmp);mapDir.put(Dir.TMP, dirTmp);
		lDirectotries.add(dirBat);mapDir.put(Dir.BAT, dirBat);
		lDirectotries.add(dirTools);mapDir.put(Dir.TOOLS, dirTools);
//		lDirectotries.add(dirCutlists);
		lDirectotries.add(dirRename);mapDir.put(Dir.RENAME, dirRename);
	}
	
	private void initToolList()
	{
		mapTool = new Hashtable<Tool,String>();
		lTools = new ArrayList<String>();
		lTools.add(toolMp4Box);mapTool.put(Tool.MP4BOX, toolMp4Box);
		lTools.add(toolLame);mapTool.put(Tool.LAME, toolLame);
		lTools.add(toolFfmpeg);mapTool.put(Tool.FFMPEG, toolFfmpeg);
		lTools.add(toolFaac);mapTool.put(Tool.FAAC, toolFaac);
	}
	
	private void initParameterList()
	{
		mapAudio = new Hashtable<Audio,String>();
		mapAudio.put(Audio.FAAC, paraAudioFaac);
	}
	
	private void initUrlList()
	{
		mapUrl = new Hashtable<Url,String>();
		mapUrl.put(Url.GAE, urlOtrSeries);
	}
	
	public void createDefault(String configFile)
	{	
		File f = new File(configFile);
		if(f.exists())
		{
			logger.info("Skipping creation of default config, file "+FilenameUtils.normalize(f.getAbsolutePath())+" exists.");
		}
		else
		{
			logger.info("Creating default configuration: "+FilenameUtils.normalize(f.getAbsolutePath()));
			try
			{
				StringBuffer sb = new StringBuffer();
				sb.append("Don't use a backslash \\ in your path!").append(SystemUtils.LINE_SEPARATOR);
				sb.append("I'm too lazy to parse this stupid windows stuff out ...").append(SystemUtils.LINE_SEPARATOR);
				sb.append("C:\\\\test\\tmp will simply be C:/test/tmp").append(SystemUtils.LINE_SEPARATOR);
				
				PropertiesConfiguration config = new PropertiesConfiguration(f);
				config.setHeader(sb.toString());
				
				config.setProperty(dirHqAvi, "HQ.avi");
				config.setProperty(dirHdAvi, "HD.avi");
				config.setProperty(dirHdAc3, "HD.ac3");
				config.setProperty(dirMp4, "MP4");
				config.setProperty(dirTmp, "tmp");
				config.setProperty(dirBat, ".");
				config.setProperty(dirTools, "OtrCutMp4.Tools");
//				config.setProperty(dirCutlists, "Cutlists");
				config.setProperty(dirRename, "Mp4.Rename");
				
				config.setProperty(toolMp4Box, "MP4Box.exe");
				config.setProperty(toolLame, "lame.exe");
				config.setProperty(toolFfmpeg, "ffmpeg.exe");
				config.setProperty(toolFaac, "faac.exe");
				
				config.setProperty(paraAudioFaac, "192");
				config.save();
			}
			catch (ConfigurationException e) {logger.error("",e);}
		}
	}
	
	public void readConfig(String configFile) throws OtrConfigurationException
	{
		config = null;
		
		File f = new File(configFile);
		if(!f.exists()){throw new OtrConfigurationException("Configuration file does not exist: "+f.getAbsolutePath());}
		if(!f.isFile()){throw new OtrConfigurationException("Configuration file is not a file: "+f.getAbsolutePath());}
		
		try{config = new PropertiesConfiguration(configFile);}
		catch (ConfigurationException e) {throw new OtrConfigurationException(e.getMessage());}
	}
	
	public void createDirs() throws OtrConfigurationException
	{
		for(String dirKey : lDirectotries)
		{
			String dirName = config.getString(dirKey);
			if(dirName!=null){createDir(dirName);}
			else {throw new OtrConfigurationException("Entry in properties file missing : "+dirKey);}
		}
	}
	
	private void createDir(String name)
	{
		File f = new File(name);
		
		if(f.exists() && f.isDirectory())
		{
			logger.debug("Directory exists. Skipping "+name);
		}
		else if(f.exists() && !f.isDirectory())
		{
			logger.warn("File already exists. But it should be a directory: "+FilenameUtils.normalize(f.getAbsolutePath()));
		}
		else
		{
			logger.debug("Creating directory "+f.getAbsolutePath());
			f.mkdirs();
		}
	}
	
	public void checkConfigSettings() throws OtrConfigurationException
	{
		checkDirs();
		checkTools();
		checkParameter();
	}
	
	private void checkDirs() throws OtrConfigurationException
	{
		for(String dirKey : lDirectotries)
		{
			String dirName = config.getString(dirKey);
			if(dirName!=null){checkDir(dirKey, dirName);}
			else {throw new OtrConfigurationException("Entry in properties file missing : "+dirKey);}
		}
	}
	
	private void checkDir(String key, String dirName) throws OtrConfigurationException
	{
		File f = new File(dirName);
		
		if(!f.exists())
		{
			throw new OtrConfigurationException("Directory ("+key+") does not exist! "+FilenameUtils.normalize(f.getAbsolutePath()));
		}
		else if(!f.isDirectory())
		{
			throw new OtrConfigurationException("File already exists. But it should be a directory: "+FilenameUtils.normalize(f.getAbsolutePath()));
		}
	}
	
	private void checkTools() throws OtrConfigurationException
	{
		File dirTools = new File(config.getString(OtrConfig.dirTools));
		for(String toolKey : lTools)
		{
			String toolName = config.getString(toolKey);
			if(toolName!=null){checkTool(dirTools,toolKey,toolName);}
			else {throw new OtrConfigurationException("Entry in properties file. Missing Key: "+toolKey);}
		}
	}
	
	private void checkTool(File dirTools, String toolKey, String toolName) throws OtrConfigurationException
	{
		File f = new File(dirTools,toolName);
		
		if(!f.exists())
		{
			throw new OtrConfigurationException("Tool ("+toolKey+") does not exist! "+FilenameUtils.normalize(f.getAbsolutePath()));
		}
	}
	
	private void checkParameter() throws OtrConfigurationException
	{
		for(Audio audio : Audio.values())
		{
			String value = config.getString(mapAudio.get(audio));
			if(value==null){throw new OtrConfigurationException("Entry in properties file. Missing Key: "+mapAudio.get(audio));}
		}
	}
	
	public File getDir(Dir dir)
	{
		if(config==null){logger.error("Throw");}
		return new File(config.getString(mapDir.get(dir)));
	}
	
	public String getTool(Tool tool)
	{
		if(config==null){logger.error("Throw");}
		return config.getString(mapTool.get(tool));
	}
	
	public String getAudio(Audio audio)
	{
		if(config==null){logger.error("Throw");}
		return config.getString(mapAudio.get(audio));
	}

	public String getUrl(Url url)
	{
		if(config==null){logger.error("Throw");}
		return config.getString(mapUrl.get(url));
	}
	
	public String getKey(String key)
	{
		return config.getString(key); 
	}
}