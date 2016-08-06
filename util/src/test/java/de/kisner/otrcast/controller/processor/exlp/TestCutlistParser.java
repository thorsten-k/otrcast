package de.kisner.otrcast.controller.processor.exlp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.core.event.JaxbEvent;
import net.sf.exlp.core.handler.EhResultContainer;
import net.sf.exlp.core.listener.LogListenerFile;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.AbstractOtrcastTest;
import de.kisner.otrcast.OtrUtilTestBootstrap;
import de.kisner.otrcast.controller.processor.exlp.parser.CutlistParser;
import de.kisner.otrcast.model.xml.cut.CutList;

public class TestCutlistParser extends AbstractOtrcastTest
{
	final static Logger logger = LoggerFactory.getLogger(TestCutlistParser.class);
	
	private static List<File> lTxt,lXml;
	
	@BeforeClass
	public static void initFiles()
	{
		lTxt = new ArrayList<File>();
		lXml = new ArrayList<File>();
		
		lTxt.add( new File("src/test/resources/data/txt/9552334.mpg.HQ.avi.cutlist"));
		lXml.add(new File("src/test/resources/data/xml/9552334.xml"));
		
		lTxt.add( new File("src/test/resources/data/txt/527787054.mpg.HQ.avi.cutlist"));
		lXml.add(new File("src/test/resources/data/xml/527787054.xml"));
		
	}
	
	@Test
	public void testInit() throws FileNotFoundException
	{
		Assert.assertEquals(lTxt.size(), lXml.size());
	}
	
	@Test
	public void test() throws FileNotFoundException
	{
		for(int i=0;i<lTxt.size();i++)
		{
			testCutlist(lTxt.get(i), lXml.get(i));
		}
		
	}
	
	private void testCutlist(File fTxt, File fXml) throws FileNotFoundException
	{
		CutList expected = JaxbUtil.loadJAXB(fXml.getPath(), CutList.class);
		CutList actual = cutlist(fTxt);
		this.assertJaxbEquals(expected, actual);
	}
	
	public CutList cutlist(File fTxt)
	{
		
		EhResultContainer leh =  new EhResultContainer();
		LogParser lp = new CutlistParser(leh);
		
		LogListener ll = new LogListenerFile(fTxt.getPath(),lp);
		ll.processSingle();
		
		JaxbEvent event = (JaxbEvent)leh.getSingleResult();
		return (CutList)event.getObject();
	}
	
	public void pattern()
	{	
		String sPattern = "^\\|([a-zA-Z]*)=(.*)";
		String sTest    = "|Goal=blabla bla blablub";
		
		logger.debug("Pattern: "+sPattern);
		logger.debug("Test:    "+sTest);
		
		Pattern p = Pattern.compile(sPattern);
		Matcher m = p.matcher(sTest);
		logger.debug("matches:"+m.matches());
		if(m.matches())
		{
			logger.debug("Group Count "+m.groupCount());
			for(int i=0;i<=m.groupCount();i++)
			{
				logger.debug(i+" "+m.group(i));
			}
		}
	}
	
	public static void main(String args[]) throws Exception
	{
		OtrUtilTestBootstrap.init();		
		
		int index = 1;
		
		TestCutlistParser.initFiles();
		TestCutlistParser test = new TestCutlistParser();
		CutList cl = test.cutlist(TestCutlistParser.lTxt.get(index));
		JaxbUtil.info(cl);
		JaxbUtil.save(TestCutlistParser.lXml.get(index), cl, true);
	}
}
