package net.sf.otrcutmp4.web.tvdb;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractTvDbQuery
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTvDbQuery.class);

	protected static final String url = "http://thetvdb.com/api";
    protected String apiKey;
    protected boolean debugPlainResponses;



	public AbstractTvDbQuery(String apiKey)
	{
        this.apiKey=apiKey;
        debugPlainResponses=false;
	}

    protected Document fetch(String queryUrl)
    {
        HttpGet get = new HttpGet(queryUrl);
        //        get.setHeader("Content-type", "text/xml; charset=UTF-8");

        HttpResponse result = null;

        HttpClient httpclient = new DefaultHttpClient();
        try
        {
            try
            {
                result = httpclient.execute(get);
            }
            catch (ClientProtocolException e) {e.printStackTrace();}
            catch (IOException e) {e.printStackTrace();}

            logger.debug("Response status code: " + result.getStatusLine());
        }
        finally
        {
        }

        Document doc = null;
        SAXBuilder builder = new SAXBuilder();

        try
        {
            doc = builder.build(new InputStreamReader(result.getEntity().getContent()));
        }
        catch (IllegalStateException e) {e.printStackTrace();}
        catch (JDOMException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        return doc;
    }
    
	public boolean isDebugPlainResponses() {return debugPlainResponses;}
	public void setDebugPlainResponses(boolean debugPlainResponses) {this.debugPlainResponses = debugPlainResponses;}
}