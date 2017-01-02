package de.kisner.otrcast.controller.processor;

import java.io.File;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.processor.SrcDirProcessor;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.test.AbstractOtrcastTest;
import de.kisner.otrcast.test.OtrCastUtilTestBootstrap;
import de.kisner.otrcast.util.query.xpath.OtrXpath;
import de.kisner.otrcast.view.noop.NoopSrcDirProcessorView;

public class TestSrcDirProcessor extends AbstractOtrcastTest
{
	final static Logger logger = LoggerFactory.getLogger(TestSrcDirProcessor.class);
	
	private SrcDirProcessor srcDirProcessor;
	private File fSrc;
	
	@Before
	public void init()
	{
		fSrc = new File("src/test/resources/data/avi");
		srcDirProcessor = new SrcDirProcessor(new NoopSrcDirProcessorView());
	}
	
    @Test
    public void size()
    {
    	VideoFiles vFiles = srcDirProcessor.scan(fSrc);
    	Assert.assertEquals(3,vFiles.getVideoFile().size());
    }
    
    @Test
    public void hq() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	String key = "Test1_11.11.11_20-15_zdf_100_TVOON_DE";
    	VideoFiles vFiles = srcDirProcessor.scan(fSrc);
    	VideoFile vf = OtrXpath.getFileByKey(vFiles, key);
    	Assert.assertEquals(key,vf.getOtrId().getKey());
    }
    
    @Test
    public void hdNoAc3() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	String key = "Test2_11.11.11_20-15_zdf_100_TVOON_DE";
    	VideoFiles vFiles = srcDirProcessor.scan(fSrc);
    	VideoFile vf = OtrXpath.getFileByKey(vFiles, key);
    	Assert.assertEquals(key,vf.getOtrId().getKey());
    	Assert.assertEquals(false, vf.getOtrId().getFormat().isAc3());
    }
    
    @Test
    public void hdAc3() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	String key = "Test3_11.11.11_20-15_zdf_100_TVOON_DE";
    	VideoFiles vFiles = srcDirProcessor.scan(fSrc);
    	VideoFile vf = OtrXpath.getFileByKey(vFiles, key);
    	Assert.assertEquals(key,vf.getOtrId().getKey());
    	Assert.assertEquals(true, vf.getOtrId().getFormat().isAc3());
    }
    
    @Test
    public void isValidSrcFileName()
    {
    	SrcDirProcessor sdp = new SrcDirProcessor(null);
    	Assert.assertFalse(sdp.isValidSrcFileName("xxx.mp4"));
    	Assert.assertTrue(sdp.isValidSrcFileName("66128_Der_Tatortreiniger_12.05.17_21-45_ard_30_TVOON_DE.mpg.HQ.cut.mp4"));
    }
    
    @Test
    public void subDirs()
    {
    	VideoFiles vFiles = srcDirProcessor.scan(fSrc,true);
    	Assert.assertEquals(4,vFiles.getVideoFile().size());
    }
    
    public static void main(String[] args) throws Exception
    {
    	OtrCastUtilTestBootstrap.init();
    	
    	TestSrcDirProcessor test = new TestSrcDirProcessor();
    	test.init();
    	test.size();
//    	test.hq();
    	test.hdNoAc3();
    }
 }