package net.sf.otrcutmp4.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.otrcutmp4.exception.OtrConfigurationException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class OtrConfig
{
	static Log logger = LogFactory.getLog(OtrConfig.class);
	
	public static String otrConfigName = "properties.txt";
		
	public static final String dirHqAvi = "dir.hq.avi";
	public static final String dirHdAc3 = "dir.hd.ac3";
	public static final String dirHdAvi = "dir.hd.avi";
	public static final String dirHqMp4 = "dir.hq.mp4";
	public static final String dirTmp = "dir.tmp";
	public static final String dirBat = "dir.bat";
	public static final String dirTools = "dir.tools";
	public static final String dirCutlists = "dir.cutlists";
	public static final String dirMp4Rename = "dir.mp4.rename";
	
	public static final String toolMp4Box = "tool.mp4box";
	public static final String toolLame = "tool.lame";
	public static final String toolFfmpeg = "tool.ffmpeg";
	public static final String toolFaac = "tool.faac";
	
	private List<String> lDirectotries,lTools;
	
	public OtrConfig()
	{
		initDirectoryList();
		initToolList();
	}
	
	private void initDirectoryList()
	{
		lDirectotries = new ArrayList<String>();
		lDirectotries.add(dirHqAvi);
		lDirectotries.add(dirHdAvi);
		lDirectotries.add(dirHdAc3);
		lDirectotries.add(dirHqMp4);
		lDirectotries.add(dirTmp);
		lDirectotries.add(dirBat);
		lDirectotries.add(dirTools);
		lDirectotries.add(dirCutlists);
		lDirectotries.add(dirMp4Rename);
	}
	
	private void initToolList()
	{
		lTools = new ArrayList<String>();
		lTools.add(toolMp4Box);
		lTools.add(toolLame);
		lTools.add(toolFfmpeg);
		lTools.add(toolFaac);
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
				config.setProperty(dirHdAvi, "HD.ac3");
				config.setProperty(dirHqMp4, "HQ.mp4");
				config.setProperty(dirTmp, "tmp");
				config.setProperty(dirBat, ".");
				config.setProperty(dirBat, "OtrCutMp4.Tools");
				config.setProperty(dirCutlists, "Cutlists");
				config.setProperty(dirMp4Rename, "Mp4.Cut.Rename");
				
				config.setProperty(toolMp4Box, "MP4Box.exe");
				config.setProperty(toolLame, "lame.exe");
				config.setProperty(toolFfmpeg, "ffmpeg.exe");
				config.setProperty(toolFaac, "faac.exe");
				config.save();
			}
			catch (ConfigurationException e) {logger.error(e);}
		}
	}
	
	public Configuration readConfig(String configFile) throws OtrConfigurationException
	{
		Configuration config = null;
		
		File f = new File(configFile);
		if(!f.exists()){throw new OtrConfigurationException("Configuration file does not exist: "+f.getAbsolutePath());}
		if(!f.isFile()){throw new OtrConfigurationException("Configuration file is not a file: "+f.getAbsolutePath());}
		
		try{config = new PropertiesConfiguration(configFile);}
		catch (ConfigurationException e) {throw new OtrConfigurationException(e.getMessage());}
		
		return config;
	}
	
	public void createDirs(Configuration config) throws OtrConfigurationException
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
	
	public void checkDirs(Configuration config) throws OtrConfigurationException
	{
		for(String dirKey : lDirectotries)
		{
			String dirName = config.getString(dirKey);
			if(dirName!=null){checkDir(dirKey, dirName);}
			else {throw new OtrConfigurationException("Entry in properties file missing : "+dirKey);}
			;
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
	
	public void checkTools(Configuration config) throws OtrConfigurationException
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
}