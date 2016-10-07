package de.kisner.otrcast.model.xml;

import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.jeesl.model.xml.JeeslNsPrefixMapper;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class OtrCastNsPrefixMapper extends NamespacePrefixMapper implements NsPrefixMapperInterface
{
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
    {
    	if     ("http://otrcutmp4.sf.net".equals(namespaceUri) ){return "otr";}
    	else if("http://otrcutmp4.sf.net/series".equals(namespaceUri) ){return "s";}
    	else if("http://otrcutmp4.sf.net/cut".equals(namespaceUri) ){return "c";}
    	else if("http://otrcutmp4.sf.net/otr".equals(namespaceUri) ){return "o";}
    	else if("http://otrcutmp4.sf.net/mc".equals(namespaceUri) ){return "mc";}
    	else if("http://otrcutmp4.sf.net/rss".equals(namespaceUri) ){return "rss";}
        else if("http://otrcutmp4.sf.net/tvdb".equals(namespaceUri) ){return "tvdb";}
        else if("http://otrcutmp4.sf.net/db".equals(namespaceUri) ){return "db";}
        else if("http://otrcutmp4.sf.net/video".equals(namespaceUri) ){return "v";}
    	else if("http://www.itunes.com/dtds/podcast-1.0.dtd".equals(namespaceUri) ){return "itunes";}
        
    	JeeslNsPrefixMapper jeeslPrefixMapper = new JeeslNsPrefixMapper();
        return jeeslPrefixMapper.getPreferredPrefix(namespaceUri, suggestion, requirePrefix);
    }

    public String[] getPreDeclaredNamespaceUris()
    {
    	String[] result = new String[3];
    	result[2] = "http://otrcutmp4.sf.net/series";
    	result = new String[0];
        return result;
    }
}