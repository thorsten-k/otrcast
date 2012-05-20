package net.sf.otrcutmp4.controller.processor;

import java.io.File;

import junit.framework.Assert;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.otrcutmp4.controller.SrcDirProcessor;
import net.sf.otrcutmp4.controller.factory.xml.XmlOtrIdFactory.VideType;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTstBootstrap;
import net.sf.otrcutmp4.view.noop.NoopSrcDirProcessorView;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSrcDirProcessor extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestSrcDirProcessor.class);
	
	private SrcDirProcessor srcDirProcessor;
	private File fSrc;
	
//	@Rule public IgnoreOtherRule test = new IgnoreOtherRule("testHdAc3Key");
	
	@Before
	public void init()
	{
		fSrc = new File("src/test/resources/data/avi");
		srcDirProcessor = new SrcDirProcessor(new NoopSrcDirProcessorView());
	}
	
	//HD
    @Test public void size()
    {
    	VideoFiles vf = srcDirProcessor.readFiles(fSrc, VideType.avi);
    	Assert.assertEquals(1,vf.getVideoFile().size());
    }
    
    public static void main(String[] args) throws ExlpConfigurationException
    {
    	OtrUtilTstBootstrap.init();
    	
    	TestSrcDirProcessor test = new TestSrcDirProcessor();
    	test.init();
    	test.size();
    }
 }