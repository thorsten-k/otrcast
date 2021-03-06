package de.kisner.otrcast.model.json;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.kisner.otrcast.test.AbstractOtrJsonTest;
import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestJsonOtrMovie extends AbstractOtrJsonTest<JsonOtrIdentifier>
{
	final static Logger logger = LoggerFactory.getLogger(TestJsonOtrMovie.class);
	
//    @Test
    public void test() throws JsonGenerationException, JsonMappingException, IOException
    {
    	JsonOtrIdentifier json = new JsonOtrIdentifier();
    	json.setScheme("myScheme");
    	json.setType("myType");
    	json.setId(123);
    	
    	jom.writeValue(System.out, json);
    }
    
    public static JsonOtrMovie create(boolean withChildren)
    {
    	JsonOtrMovie json = new JsonOtrMovie();
    	json.setId(123);
    	json.setYear(2015);
    	return json;
    }
  
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException
    {
		OtrXmlTestBootstrap.init();
			
		TestJsonOtrMovie test = new TestJsonOtrMovie();
		TestJsonOtrMovie.initJson();
		test.test();
    }
}