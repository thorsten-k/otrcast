package net.sf.otrcutmp4.controller.processor.hotfolder;

import java.io.File;

import net.sf.otrcutmp4.interfaces.processor.CallbackInterface;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallbackProcessor implements Processor
{
	final static Logger logger = LoggerFactory.getLogger(CallbackProcessor.class);
	
	private CallbackInterface callback;
	
	public CallbackProcessor(CallbackInterface callback)
	{
		this.callback=callback;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void process(Exchange exchange) throws Exception
	{
			GenericFile file = (GenericFile) exchange.getIn().getBody();
			File jFile       = (File)file.getFile();
			logger.info(jFile.getAbsolutePath());
			callback.processing(jFile);
	}		
}