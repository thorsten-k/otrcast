package net.sf.otrcutmp4.web;

import java.io.FileNotFoundException;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.controller.factory.xml.XmlQualityFactory;
import net.sf.otrcutmp4.controller.processor.exlp.LinklistConverter;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.Link;
import net.sf.otrcutmp4.model.xml.otr.Linklist;
import net.sf.otrcutmp4.model.xml.otr.OtrId;
import net.sf.otrcutmp4.model.xml.otr.Quality;
import net.sf.otrcutmp4.model.xml.otr.Recording;
import net.sf.otrcutmp4.test.AbstractUtilTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestWebCutListLoader extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWebCutListLoader.class);
	
	private static final String id ="9552334";
	
 }