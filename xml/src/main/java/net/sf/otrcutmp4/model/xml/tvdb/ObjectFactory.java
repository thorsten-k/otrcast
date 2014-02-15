
package net.sf.otrcutmp4.model.xml.tvdb;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.sf.otrcutmp4.model.xml.tvdb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.sf.otrcutmp4.model.xml.tvdb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Sync }
     * 
     */
    public Sync createSync() {
        return new Sync();
    }

    /**
     * Create an instance of {@link Banners }
     * 
     */
    public Banners createBanners() {
        return new Banners();
    }

    /**
     * Create an instance of {@link Banner }
     * 
     */
    public Banner createBanner() {
        return new Banner();
    }

}
