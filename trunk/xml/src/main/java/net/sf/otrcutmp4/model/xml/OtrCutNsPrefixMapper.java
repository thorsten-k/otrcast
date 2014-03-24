package net.sf.otrcutmp4.model.xml;

import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class OtrCutNsPrefixMapper extends NamespacePrefixMapper implements NsPrefixMapperInterface
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
    	else if("http://www.itunes.com/dtds/podcast-1.0.dtd".equals(namespaceUri) ){return "itunes";}
        
        return suggestion;
    }

    public String[] getPreDeclaredNamespaceUris()
    {
    	String[] result = new String[3];
    	result[2] = "http://otrcutmp4.sf.net/series";
    	result = new String[0];
        return result;
    }
}