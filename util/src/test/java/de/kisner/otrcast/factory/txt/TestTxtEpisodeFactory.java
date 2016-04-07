package de.kisner.otrcast.factory.txt;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.AbstractUtilTest;
import freemarker.template.TemplateException;

public class TestTxtEpisodeFactory extends AbstractUtilTest
{ 
	final static Logger logger = LoggerFactory.getLogger(TestTxtEpisodeFactory.class);
	
	@Test
	public void simple() throws IOException, TemplateException
	{
		String expected = "This is a test";
		Assert.assertEquals(expected, TxtEpisodeFactory.buld(expected));
	}
	
	@Test
	public void space() throws IOException, TemplateException
	{
		String expected = "This is a test";
		Assert.assertEquals(expected+"...", TxtEpisodeFactory.buld(expected+"..."));
	}
	
	@Test
	public void noSpace() throws IOException, TemplateException
	{
		String expected = "This is a test";
		Assert.assertEquals(expected+" ...", TxtEpisodeFactory.buld(expected+" ..."));
	}
	
	@Test @Ignore
	public void modify() throws IOException, TemplateException
	{
		String expected = "This is a test";
		Assert.assertEquals(expected+"...", TxtEpisodeFactory.buld(expected+" ..."));
	}
}