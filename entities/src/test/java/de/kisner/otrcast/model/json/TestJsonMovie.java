package de.kisner.otrcast.model.json;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.kisner.otrcast.test.AbstractOtrJsonTest;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestJsonMovie extends AbstractOtrJsonTest
{
	final static Logger logger = LoggerFactory.getLogger(TestJsonMovie.class);
	
//    @Test
    public void test() throws JsonGenerationException, JsonMappingException, IOException
    {
    	JsonVideoIdentifier json = new JsonVideoIdentifier();
    	json.setScheme("myScheme");
    	json.setType("myType");
    	json.setId(123);
    	
    	jom.writeValue(System.out, json);
    }
    
    public static JsonMovie build(boolean withChildren)
    {
    	JsonMovie json = new JsonMovie();
    	json.setId(123);
    	json.setYear(2015);
    	return json;
    }
  
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException
    {
		OtrXmlTstBootstrap.init();
			
		TestJsonMovie test = new TestJsonMovie();
		TestJsonMovie.initJson();
		test.test();
    }
}