package net.sf.otrcutmp4;

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
	public static final String dirHqMp4 = "dir.hq.mp4";
	public static final String dirTmp = "dir.tmp";
	public static final String dirCutlists = "dir.cutlists";
	
	private List<String> lDirectotries;
	
	public OtrConfig()
	{
		lDirectotries = new ArrayList<String>();
		lDirectotries.add(dirHqAvi);
		lDirectotries.add(dirHqMp4);
		lDirectotries.add(dirTmp);
		lDirectotries.add(dirCutlists);
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
				sb.append("C:\\\\test\\tmp will simply be C://test/test").append(SystemUtils.LINE_SEPARATOR);
				
				PropertiesConfiguration config = new PropertiesConfiguration(f);
				config.setHeader(sb.toString());
				
				config.setProperty(dirHqAvi, "HQ.avi");
				config.setProperty(dirHqMp4, "HQ.mp4");
				config.setProperty(dirTmp, "tmp");
				config.setProperty(dirCutlists, "Cutlists");
				
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
	
	public void createDirs(Configuration config)
	{
		for(String dirName : lDirectotries)
		{
			createDir(config.getString(dirName));
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
		for(String dirName : lDirectotries)
		{
			checkDir(dirName, config.getString(dirName));
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
}