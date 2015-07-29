package de.kisner.otrcast.web.tvdb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.factory.xml.tvdb.XmlBannerFactory;
import de.kisner.otrcast.factory.xml.tvdb.XmlSyncFactory;
import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.series.Series;
import de.kisner.otrcast.model.xml.tvdb.Banners;
import de.kisner.otrcast.web.tvdb.processor.TvDbBannerFactory;
import de.kisner.otrcast.web.tvdb.processor.TvDbSeriesStructureFactory;

public class TvDbSeriesQuery extends AbstractTvDbQuery
{
	final static Logger logger = LoggerFactory.getLogger(TvDbSeriesQuery.class);
	
	public TvDbSeriesQuery(String apiKey)
	{
        super(apiKey);
	}
	
	public Otr findSeriesUrlEncode(String name) throws UnsupportedEncodingException, UtilsProcessingException
	{
		return findSeries(URLEncoder.encode(name,"UTF-8"));
	}
	public Otr findSeries(String name) throws UtilsProcessingException
	{
        Otr otr = new Otr();

        Document doc = fetch(url+"/GetSeries.php?seriesname="+name);

        if(debugPlainResponses)
        {
        	XMLOutputter serializer= new XMLOutputter(Format.getPrettyFormat());
        	logger.debug(serializer.outputString(doc));
        }

        XPathFactory xPathFactory = XPathFactory.instance();
        XPathExpression<Element> xPathExpression = xPathFactory.compile("/Data/Series", Filters.element());
        for(Element e : xPathExpression.evaluate(doc))
        {
            Series series = new Series();

            series.setName(e.getChildText("SeriesName"));
            series.setSync(XmlSyncFactory.build(e.getChildText("id")));

            if(e.getChildText("banner")!=null)
            {
                series.getSync().setBanners(new Banners());
                series.getSync().getBanners().getBanner().add(XmlBannerFactory.build(e.getChildText("banner")));
            }

            otr.getSeries().add(series);
        }
        JaxbUtil.trace(otr);
        return otr;
	}

    public Otr querySeries(long tvDbSeriesId) throws JDOMException
    {
        HttpGet get = new HttpGet(url+"/"+apiKey+"/series/"+tvDbSeriesId+"/all/de.zip");
        HttpResponse result = null;

        HttpClient httpclient = new DefaultHttpClient();
        try
        {
            result = httpclient.execute(get);
            logger.debug("Response status code: " + result.getStatusLine());
        }
        catch (ClientProtocolException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}

        Otr otr = new Otr();
        
        try
        {
            InputStream is = result.getEntity().getContent();
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry ze;
//            byte[] buff = new byte[1024];
            while((ze=zis.getNextEntry())!=null)
            {

                logger.info(ze.getName());
                try
                {
                    ByteArrayOutputStream output = new ByteArrayOutputStream();

                    int data = 0;
                    while( ( data = zis.read() ) != - 1 )
                    {
                        output.write( data );
                    }
                    output.close();

                    Document doc = null;
                    SAXBuilder builder = new SAXBuilder();

                    Reader reader = new InputStreamReader(new ByteArrayInputStream(output.toByteArray()));
                    doc = builder.build(reader);
                    File file = new File("/Volumes/ramdisk/",ze.getName());
                    JDomUtil.save(doc, file, Format.getPrettyFormat());
                    
                    
                    if(ze.getName().equals("de.xml"))
                    {
                    	TvDbSeriesStructureFactory f = new TvDbSeriesStructureFactory(doc);
                    	otr.getSeries().add(f.build());
                    }
                    else if(ze.getName().equals("banners.xml"))
                    {
                    	TvDbBannerFactory f = new TvDbBannerFactory(doc);
                    	otr.getBanners().add(f.build());
                    }
//                    

                }
                catch (IllegalStateException e) {e.printStackTrace();}
                catch (IOException e) {e.printStackTrace();}

            }
            zis.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return otr;
    }
}