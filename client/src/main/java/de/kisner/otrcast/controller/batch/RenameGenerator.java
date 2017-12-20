package de.kisner.otrcast.controller.batch;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.rest.OtrVideoRest;
import de.kisner.otrcast.factory.txt.TxtDsFactory;
import de.kisner.otrcast.factory.txt.TxtFileNameFactoy;
import de.kisner.otrcast.interfaces.OtrCastInterface;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.series.Tags;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Dir;
import de.kisner.otrcast.util.OtrConfig.Template;
import de.kisner.otrcast.util.OtrConfig.Url;
import freemarker.template.TemplateException;
import net.sf.ahtutils.web.rest.auth.RestEasyPreemptiveClientExecutor;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.cmd.ShellCmdMove;
import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;

public class RenameGenerator extends AbstactBatchGenerator
{
	final static Logger logger = LoggerFactory.getLogger(RenameGenerator.class);
	
	private File dirHqMp4,dirMp4Rename;
	private ExlpTxtWriter txt;
	
	private RelativePathFactory rpf;
	private OtrVideoRest rest;
	
	private TxtDsFactory fTemplateDs;
	private TxtFileNameFactoy fnfSeries;
	
	public RenameGenerator(OtrConfig cfg, OtrCastInterface.Profile profile)
	{
		super(cfg,profile);
		
		fTemplateDs = new TxtDsFactory();
		fnfSeries = new TxtFileNameFactoy();
		fnfSeries.initTemplate(cfg.getTemplate(Template.fnSeries));
		
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory("user","pwd");
		rest = ProxyFactory.create(OtrVideoRest.class, cfg.getUrl(Url.OTR),clientExecutor);
		
		dirHqMp4 = cfg.getDir(Dir.MP4);
		logger.warn("NYI: You need to set dirMp4Rename from somewhere!!");
		//dirMp4Rename = cfg.getDir(Dir.RENAME);
		
		logger.debug("");
		logger.debug("Creating Batch in "+cfg.getDir(Dir.BAT));
		
		txt = new ExlpTxtWriter();
		rpf = new RelativePathFactory(RelativePathFactory.PathSeparator.CURRENT,true);
		
		try {ShellCmdMove.moveFile("a", "b");}
		catch (ExlpUnsupportedOsException e)
		{
			logger.error("",e.getMessage());
			logger.error("No move command available for this architecture.");
			logger.error("System will terminate");
		}
	}
	
	public void create(VideoFiles vFiles)
	{
		 for(VideoFile vf : vFiles.getVideoFile())
		 {
			 try
			 {
				crateForVideo(vf);
			 }
			 catch (TemplateException e) {logger.error(e.getMessage());}
			 catch (IOException e) {logger.error(e.getMessage());}
		 }

		 File f = new File(cfg.getDir(Dir.BAT), "otrRename.bat");
		 txt.writeFile(f);
		 logger.info("");
		 logger.info("Batch file written to: "+rpf.relativate(new File("."), f));
	}
	
	private void crateForVideo(VideoFile vf) throws TemplateException, IOException
	{	
		String sOriginal = rpf.relativate(cfg.getDir(Dir.BAT), new File(dirMp4Rename,vf.getFileName().getValue()));
		Tags tags = rest.getTags(vf.getFileName().getValue());
		
		if(tags.getTag().size()>0)
		{
			Map<String,String> ds = fTemplateDs.build(tags);
			String sTo = rpf.relativate(cfg.getDir(Dir.BAT), new File(dirHqMp4,fnfSeries.convert(ds)+".mp4"));
			
			try {txt.add(ShellCmdMove.moveFile(sOriginal, sTo));}
			catch (ExlpUnsupportedOsException e)
			{
				logger.error("",e);
				logger.error("File will not be copied! ");
				logger.error("\tFrom: "+sOriginal);
				logger.error("\tTo  : "+sTo);
			}
		}
		else
		{
			logger.info("No tag found for "+vf.getFileName().getValue());
		}
	}
}