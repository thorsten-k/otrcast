package de.kisner.otrcast.factory.txt;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractOtrcastTest;

public class TestTxtEpisodeFactory extends AbstractOtrcastTest
{ 
	final static Logger logger = LoggerFactory.getLogger(TestTxtEpisodeFactory.class);
	
	private static String txt = "This is a test";
	
	
	@Test
	public void simple()
	{
		Assert.assertEquals("No dots", txt, TxtEpisodeFactory.buld(txt));
		Assert.assertEquals(txt+" "+TxtEpisodeFactory.dots, TxtEpisodeFactory.buld(txt+" "+TxtEpisodeFactory.dots));
		Assert.assertEquals(TxtEpisodeFactory.dots+txt, TxtEpisodeFactory.buld(TxtEpisodeFactory.dots+txt));
		Assert.assertEquals(TxtEpisodeFactory.dots+" "+txt, TxtEpisodeFactory.buld(TxtEpisodeFactory.dots+" "+txt));
	}
	
	@Test
	public void modifyWord()
	{
		Assert.assertEquals(txt+" "+TxtEpisodeFactory.dots, TxtEpisodeFactory.buld(txt+TxtEpisodeFactory.dots));
		Assert.assertEquals(txt+" "+TxtEpisodeFactory.dots+"?", TxtEpisodeFactory.buld(txt+TxtEpisodeFactory.dots+"?"));
	}
	
	@Test
	public void regex()
	{
		Pattern p = Pattern.compile(TxtEpisodeFactory.dotPattern);
		Assert.assertTrue(p.matcher(txt+TxtEpisodeFactory.dots).matches());
		Assert.assertTrue(p.matcher(txt+"0"+TxtEpisodeFactory.dots).matches());
		Assert.assertTrue(p.matcher(txt+TxtEpisodeFactory.dots+"?").matches());
		Assert.assertFalse(p.matcher(txt+" "+TxtEpisodeFactory.dots).matches());
	}
}