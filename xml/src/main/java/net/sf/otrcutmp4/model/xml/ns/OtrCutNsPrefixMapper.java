package net.sf.otrcutmp4.model.xml.ns;

import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class OtrCutNsPrefixMapper extends NamespacePrefixMapper implements NsPrefixMapperInterface
{
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
    {
    	if("http://otrcutmp4.sf.net".equals(namespaceUri) ){return "otr";}
        if("http://otrcutmp4.sf.net/series".equals(namespaceUri) ){return "s";}
        if("http://otrcutmp4.sf.net/cut".equals(namespaceUri) ){return "c";}
        if("http://otrcutmp4.sf.net/otr".equals(namespaceUri) ){return "o";}
        
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