package de.kisner.otrcast.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrConfigurationException;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class OtrConfig
{
	final static Logger logger = LoggerFactory.getLogger(OtrConfig.class);
	
	public static enum Dir{TMP,BAT,RENAME,TOOLS,MP4,AVI,COVER,MC,BACKUP,IN,DB};
	public static enum Tool{LAME,MP4BOX,FFMPEG,FAAC,EAC3TO,NEROAAC};
	public static enum Audio{FAAC};
	public static enum Url{OTR};
	public static enum Credential{EMAIL,PWD}
	public static enum Template{fnSeries}
	public static enum Cmd{TAGGER}
	
	public static String otrConfigName = "properties.txt";
		
	public static final String dirMc = "dir.mc.library";
	public static final String dirIncoming = "dir.mc.incoming";
	public static final String dirMcBackup = "dir.mc.backup";
	public static final String dirAvi = "dir.avi";
	public static final String dirMp4 = "dir.mp4";
	public static final String dirTmp = "dir.tmp";
	public static final String dirBat = "dir.bat";
	public static final String dirTools = "dir.tools";
	public static final String dirCutlists = "dir.cutlists";
	public static final String dirRename = "dir.rename";
	public static final String dirCover = "dir.cover";
    public static final String dirDb = "dir.db";
    
    public static final String fileMcXmlLib = "file.mc.lib.xml";
	
	public static final String toolMp4Box = "tool.mp4box";
	public static final String toolLame = "tool.lame";
	public static final String toolFfmpeg = "tool.ffmpeg";
	public static final String toolFaac = "tool.faac";
	public static final String toolEac3to = "tool.eac3to";
	public static final String toolNeroAac = "tool.neroaac";
	
	public static final String paraAudioFaac = "audio.faac.kbit";
	
	public static final String credentialEmail = "user.email";
	public static final String credentialPassword = "user.password";
	
	public static final String templateSeries = "template.series";
	
	public static final String urlOtrSeries = "url.otrseries";
	
	public static final String cmdTagger = "cmd.tagger";
	
	private Map<Dir,String> mapDir;
	private Map<Tool,String> mapTool;
	private Map<Audio,String> mapAudio;
	private Map<Url,String> mapUrl;
	private Map<Credential,String> mapCredential;
	private Map<Template,String> mapTemplate;
	private Map<Cmd,String> mapCmd;
	
	private List<String> lCutDirectotries,lMcDirectotries,lTools;
	private Configuration config;
	
	public OtrConfig(){this(null);}
	public OtrConfig(Configuration config)
	{
		this.config=config;
		initDirectoryList();
		initToolList();
		initParameterList();
		initUrlList();
		initTemplateList();
		initCredentialList();
		initCmdList();
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
	
	public void readConfig(Configuration config)
	{
		this.config=config;
	}
	
	private void initDirectoryList()
	{
		mapDir = new Hashtable<Dir,String>();
		lCutDirectotries = new ArrayList<String>();
		lCutDirectotries.add(dirAvi);mapDir.put(Dir.AVI, dirAvi);
		lCutDirectotries.add(dirMp4);mapDir.put(Dir.MP4, dirMp4);
		lCutDirectotries.add(dirTmp);mapDir.put(Dir.TMP, dirTmp);
		lCutDirectotries.add(dirBat);mapDir.put(Dir.BAT, dirBat);
		lCutDirectotries.add(dirTools);mapDir.put(Dir.TOOLS, dirTools);
		lCutDirectotries.add(dirCover);mapDir.put(Dir.COVER, dirCover);
//		lDirectotries.add(dirCutlists);
		lCutDirectotries.add(dirRename);mapDir.put(Dir.RENAME, dirRename);
		
		lMcDirectotries = new ArrayList<String>();
		mapDir.put(Dir.MC, dirMc);
		lMcDirectotries.add(dirIncoming);mapDir.put(Dir.IN, dirIncoming);
        lMcDirectotries.add(dirDb);mapDir.put(Dir.DB, dirDb);
        lMcDirectotries.add(dirMcBackup);mapDir.put(Dir.BACKUP, dirMcBackup);
	}
	
	private void initToolList()
	{
		mapTool = new Hashtable<Tool,String>();
		lTools = new ArrayList<String>();
		lTools.add(toolMp4Box);mapTool.put(Tool.MP4BOX, toolMp4Box);
		lTools.add(toolLame);mapTool.put(Tool.LAME, toolLame);
		lTools.add(toolFfmpeg);mapTool.put(Tool.FFMPEG, toolFfmpeg);
		lTools.add(toolFaac);mapTool.put(Tool.FAAC, toolFaac);
		lTools.add(toolEac3to);mapTool.put(Tool.EAC3TO, toolEac3to);
		lTools.add(toolNeroAac);mapTool.put(Tool.NEROAAC, toolNeroAac);
	}
	
	private void initParameterList()
	{
		mapAudio = new Hashtable<Audio,String>();
		mapAudio.put(Audio.FAAC, paraAudioFaac);
	}
	
	private void initCredentialList()
	{
		mapCredential = new Hashtable<Credential,String>();
		mapCredential.put(Credential.EMAIL, credentialEmail);
		mapCredential.put(Credential.PWD, credentialPassword);
	}
	
	private void initTemplateList()
	{
		mapTemplate = new Hashtable<Template,String>();
		mapTemplate.put(Template.fnSeries, templateSeries);
	}
	
	private void initUrlList()
	{
		mapUrl = new Hashtable<Url,String>();
		mapUrl.put(Url.OTR, urlOtrSeries);
	}
	
	private void initCmdList()
	{
		mapCmd = new Hashtable<Cmd,String>();
		mapCmd.put(Cmd.TAGGER, cmdTagger);
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
				
				config.setProperty(dirAvi, "AVI");
				config.setProperty(dirMp4, "MP4");
				config.setProperty(dirTmp, "tmp");
				config.setProperty(dirBat, ".");
				config.setProperty(dirTools, "OtrCutMp4.Tools");
//				config.setProperty(dirCutlists, "Cutlists");
				config.setProperty(dirRename, "Mp4.Rename");
                config.setProperty(dirDb, "DB");
				
				config.setProperty(toolMp4Box, "MP4Box.exe");
				config.setProperty(toolLame, "lame.exe");
				config.setProperty(toolFfmpeg, "ffmpeg.exe");
				config.setProperty(toolFaac, "faac.exe");
				config.setProperty(toolEac3to, "nero.exe");
				config.setProperty(toolNeroAac, "nero.exe");
						
				config.setProperty(paraAudioFaac, "192");
				
				mapCredential.put(Credential.EMAIL, credentialEmail);
				mapCredential.put(Credential.PWD, credentialPassword);
				config.setProperty(credentialEmail, "your@e.mail");
				config.setProperty(credentialPassword, "your-secret-password");
				
				config.setProperty(templateSeries, "${seriesName} ${seasonNr}x${episodeNr} ${episodeName}");
				config.setProperty(cmdTagger, "java -jar ........");
				config.save();
			}
			catch (ConfigurationException e) {logger.error("",e);}
		}
	}
		
	public void createDirs() throws OtrConfigurationException
	{
		for(String dirKey : lCutDirectotries)
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
	
	public void checkCutSettings() throws OtrConfigurationException
	{
		checkDirs(lCutDirectotries);
		checkTools();
		checkParameter();
		checkTemplates();
		checkCmds();
	}
	
	public void checkMcSettings() throws OtrConfigurationException
	{
		checkDirs(lMcDirectotries);
		
		String key = mapDir.get(Dir.MC);
		List<Object> list = config.getList(key);
		for(Object o : list)
		{
			String dir = (String)o;
			checkDir(key,dir);
		}		
	}
	
	private void checkDirs(List<String> list) throws OtrConfigurationException
	{
		for(String dirKey : list)
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
	
	public void checkEmailPwd() throws OtrConfigurationException
	{
		for(Credential credential : mapCredential.keySet())
		{
			String value = config.getString(mapCredential.get(credential));
			if(value==null){throw new OtrConfigurationException("Entry in properties file missing : "+mapCredential.get(credential));}
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
	
	private void checkTemplates() throws OtrConfigurationException
	{
		for(Template template : Template.values())
		{
			String value = config.getString(mapTemplate.get(template));
			if(value==null){throw new OtrConfigurationException("Entry in properties file. Missing Key: "+mapTemplate.get(template));}
		}
	}
	
	private void checkCmds() throws OtrConfigurationException
	{
		for(Cmd cmd : Cmd.values())
		{
			String value = config.getString(mapCmd.get(cmd));
			if(value==null){throw new OtrConfigurationException("Entry in properties file. Missing Key: "+mapCmd.get(cmd));}
		}
	}
	
	public File getDir(Dir dir)
	{
		checkPreconditions();
		String key = mapDir.get(dir);
		String fName = config.getString(key);
		return new File(fName);
	}
	
	public File getFile(String cfgKey)
	{
		return new File(config.getString(cfgKey));
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
	
	public String getTemplate(Template template)
	{
		if(config==null){logger.error("Throw");}
		return config.getString(mapTemplate.get(template));
	}
	
	public String getCmd(Cmd cmd)
	{
		if(config==null){logger.error("Throw");}
		return config.getString(mapCmd.get(cmd));
	}
	
	public String getCredential(Credential credential, String defaultCredential)
	{
		if(config==null){logger.error("Throw");}
		String result = config.getString(mapCredential.get(credential));
		if(result==null){result=defaultCredential;}
		return result;
	}
	
	private void checkPreconditions()
	{
		if(config==null){throw new RuntimeException("config is null!");}
		if(mapDir==null){throw new RuntimeException("mapDir is null!");}
	}
	
	public String getKey(String key)
	{
		return config.getString(key); 
	}
}