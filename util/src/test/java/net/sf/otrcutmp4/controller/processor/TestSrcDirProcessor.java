package net.sf.otrcutmp4.controller.processor;

import java.io.File;

import junit.framework.Assert;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.SrcDirProcessor;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
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
    @Test
    public void size()
    {
    	VideoFiles vFiles = srcDirProcessor.readFiles(fSrc);
    	Assert.assertEquals(3,vFiles.getVideoFile().size());
    }
    
    @Test
    public void hqNoAc3()
    {
    	VideoFiles vFiles = srcDirProcessor.readFiles(fSrc);
    	VideoFile vf = vFiles.getVideoFile().get(1);
    	Assert.assertEquals("test2",vf.getOtrId().getKey());
    }
    
    @Test
    public void hqAc3()
    {
    	VideoFiles vFiles = srcDirProcessor.readFiles(fSrc);
    	VideoFile vf = vFiles.getVideoFile().get(2);
    	Assert.assertEquals("test3",vf.getOtrId().getKey());
    }
    
    @Test
    public void hd()
    {
    	VideoFiles vFiles = srcDirProcessor.readFiles(fSrc);
    	VideoFile vf = vFiles.getVideoFile().get(1);
    	JaxbUtil.debug(vf);
    	Assert.assertEquals("test2",vf.getOtrId().getKey());
    }
    
    public static void main(String[] args) throws ExlpConfigurationException
    {
    	OtrUtilTstBootstrap.init();
    	
    	TestSrcDirProcessor test = new TestSrcDirProcessor();
    	test.init();
//    	test.size();
//    	test.hq();
    	test.hd();
    }
 }