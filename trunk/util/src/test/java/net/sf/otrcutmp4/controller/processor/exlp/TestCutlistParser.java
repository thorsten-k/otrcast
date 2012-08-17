package net.sf.otrcutmp4.controller.processor.exlp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.event.handler.EhResultContainer;
import net.sf.exlp.event.impl.JaxbEvent;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerFile;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.processor.exlp.parser.CutlistParser;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCutlistParser extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestCutlistParser.class);
	
	public static File fClTxt,fClXml;
	
	@BeforeClass
	public static void initFiles()
	{
		fClTxt = new File("src/test/resources/data/txt/9552334.mpg.HQ.avi.cutlist");
		fClXml = new File("src/test/resources/data/xml/9552334.xml");
	}
	
	@Test
	public void test() throws FileNotFoundException
	{
		CutList expected = JaxbUtil.loadJAXB(fClXml.getPath(), CutList.class);
		CutList actual = cutlist();
		this.assertJaxbEquals(expected, actual);
	}
	
	public CutList cutlist()
	{
		String txt = "src/test/resources/data/txt/9552334.mpg.HQ.avi.cutlist";
		logger.debug("Loading from file: "+txt);
		
		EhResultContainer leh =  new EhResultContainer();
		LogParser lp = new CutlistParser(leh);
		
		LogListener ll = new LogListenerFile(fClTxt.getPath(),lp);
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
		OtrUtilTstBootstrap.init();		
		
		TestCutlistParser.initFiles();
		TestCutlistParser test = new TestCutlistParser();
		CutList cl = test.cutlist();
		JaxbUtil.info(cl);
		JaxbUtil.save(TestCutlistParser.fClXml, cl, true);
	}
}
